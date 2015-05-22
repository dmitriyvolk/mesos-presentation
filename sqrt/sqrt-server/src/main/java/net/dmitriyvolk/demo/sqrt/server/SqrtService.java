package net.dmitriyvolk.demo.sqrt.server;

import net.dmitriyvolk.demo.sqrt.server.domain.Input;
import net.dmitriyvolk.demo.sqrt.server.domain.SqrtResult;
import net.dmitriyvolk.demo.sqrt.server.domain.WorkItem;
import net.dmitriyvolk.demo.sqrt.server.domain.WorkResult;
import net.dmitriyvolk.demo.sqrt.server.db.InputRepository;
import net.dmitriyvolk.demo.sqrt.server.db.ResultRepository;
import net.dmitriyvolk.demo.sqrt.server.db.WorkItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqrtService {

    @Value(value = "${maxDelta}")
    private Float maxDelta;

    @Autowired
    private WorkItemsRepository workItemsRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void addInput(Input input) {
        saveWorkItem(new WorkItem(input));
    }

    public WorkItem nextWorkItem() {
        WorkItem workItem;
        List<WorkItem> tempResult = workItemsRepository.findFirstByOrderByIterationNumberDesc();
        if (tempResult == null || tempResult.isEmpty()) {
            return null;
        }
        workItem = tempResult.get(0);
        removeWorkItem(workItem);
        return workItem;
    }

    private void removeWorkItem(WorkItem workItem) {
        WorkItemId id = new WorkItemId(workItem.getId());
        workItemsRepository.delete(workItem);
        simpMessagingTemplate.convertAndSend("/updates/removeworkitem", id);
    }

    public static class WorkItemId {
        private long id;

        public long getId() {
            return id;
        }

        public WorkItemId(long id) {
            this.id = id;
        }

        public WorkItemId() {

        }
    }

    public void updateWork(WorkResult wr) {
        if (withinMaxDelta(wr)) {
            createResult(wr);
        } else {
            createWorkItem(wr);
        }
    }

    private void createWorkItem(WorkResult wr) {
        WorkItem workItem = new WorkItem(wr);
        saveWorkItem(workItem);
    }

    private void saveWorkItem(WorkItem workItem) {
        workItemsRepository.save(workItem);
        sendMessage(workItem);
    }

    private WorkItem sendMessage(WorkItem workItem) {
        simpMessagingTemplate.convertAndSend("/updates/addworkitem", workItem);
        return workItem;
    }

    private void createResult(WorkResult wr) {
        SqrtResult sqrtResult = new SqrtResult(wr);
        resultRepository.save(sqrtResult);
        sendMessage(sqrtResult);
    }

    private SqrtResult sendMessage(SqrtResult sqrtResult) {
        simpMessagingTemplate.convertAndSend("/updates/addresult", sqrtResult);
        return sqrtResult;
    }

    private boolean withinMaxDelta(WorkResult wr) {
        float approximation = wr.getApproximation();
        long square = wr.getSquare();
        return (Math.abs(square - approximation * approximation) / square) < maxDelta;
    }

    public Iterable<WorkItem> getAllWorkItems() {
        return workItemsRepository.findAll();
    }

}
