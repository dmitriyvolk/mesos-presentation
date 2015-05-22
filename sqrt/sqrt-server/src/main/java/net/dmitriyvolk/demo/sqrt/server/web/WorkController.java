package net.dmitriyvolk.demo.sqrt.server.web;


import net.dmitriyvolk.demo.sqrt.server.domain.Input;
import net.dmitriyvolk.demo.sqrt.server.domain.WorkItem;
import net.dmitriyvolk.demo.sqrt.server.domain.WorkResult;
import net.dmitriyvolk.demo.sqrt.server.SqrtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping
public class WorkController {

    @Autowired
    private SqrtService service;

    Random rnd = new Random(System.currentTimeMillis());

    @RequestMapping(value = "/workitems/next", method = RequestMethod.GET)
    public WorkItem newWorkItem() {
        return service.nextWorkItem();
    }

    @RequestMapping(value = "/workitems/result", method = RequestMethod.POST)
    public void update(@RequestBody WorkResult workResult) {
        service.updateWork(workResult);
    }

    @RequestMapping(value = "/newinput/{square}", method = RequestMethod.POST)
    public void addInput(@PathVariable("square") long square) {
        service.addInput(new Input(square));
    }

    @RequestMapping(value = "/newinput/random", method = RequestMethod.POST)
    public void addRandomInput() {
        service.addInput(new Input(Math.abs(rnd.nextInt())));
    }

    @RequestMapping(value = "/workitems/havemore", method = RequestMethod.GET)
    public HaveMore haveMoreItems() {
        return new HaveMore(service.getAllWorkItems().iterator().hasNext());
    }
}
