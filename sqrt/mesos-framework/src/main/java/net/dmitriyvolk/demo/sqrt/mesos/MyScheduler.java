package net.dmitriyvolk.demo.sqrt.mesos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mesos.Protos;
import org.apache.mesos.Scheduler;
import org.apache.mesos.SchedulerDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyScheduler implements Scheduler {

    private Log log = LogFactory.getLog(this.getClass());

    private final HasMoreChecker checker;
    private final String imageName;

    /** Task ID generator. */
    private final AtomicInteger taskIDGenerator = new AtomicInteger();

    private final Map<String, Double> requiredResources = new HashMap<String, Double>();

    public MyScheduler(HasMoreChecker checker, String imageName) {
        this.checker = checker;
        this.imageName = imageName;
        requiredResources.put("cpus", 0.5D);
        requiredResources.put("mem", 512D);
    }

    static boolean isEnoughOffered(Protos.Offer offer, Map<String, Double> requiredResources) {
        for (Protos.Resource resource : offer.getResourcesList()) {
            String name = resource.getName();
            Double required = requiredResources.get(name);
            if (required != null) {
                if (resource.getScalar().getValue() < required)
                    return false;
            }
        }
        return true;
    }

    @Override
    public void registered(SchedulerDriver schedulerDriver, Protos.FrameworkID frameworkID, Protos.MasterInfo masterInfo) {
        log.info("Re-registered()");
    }

    @Override
    public void reregistered(SchedulerDriver schedulerDriver, Protos.MasterInfo masterInfo) {
        log.info("Registered with " + masterInfo.toString());
    }

    @Override
    public void resourceOffers(SchedulerDriver schedulerDriver, List<Protos.Offer> list) {
        log.info("Called resourceOffers() with " + list.size());

        for (Protos.Offer offer : list) {
            if (checker.hasMore()) {
                if (isEnoughOffered(offer, requiredResources)) {
                    // generate a unique task ID
                    Protos.TaskID taskId = Protos.TaskID.newBuilder()
                            .setValue("sqrt-task-" + Integer.toString(taskIDGenerator.incrementAndGet())).build();
                    log.info("Launching task " + taskId.getValue());


                    Protos.ContainerInfo.Builder containerBuilder = Protos.ContainerInfo.newBuilder()
                            .setType(Protos.ContainerInfo.Type.DOCKER)
                            .setDocker(Protos.ContainerInfo.DockerInfo.newBuilder()
                                    .setImage(imageName)
                                    .setNetwork(Protos.ContainerInfo.DockerInfo.Network.valueOf("HOST"))
                                    .build());

                    Protos.TaskInfo task = setResoursesRequirements(Protos.TaskInfo.newBuilder()
                            .setTaskId(taskId)
                            .setName("Sqrt Task #" + taskId.getValue())
                            .setSlaveId(offer.getSlaveId()))
                            .setContainer(containerBuilder)
                            .setCommand(Protos.CommandInfo.newBuilder().setShell(false))
                            .build();

                    Protos.Filters filters = Protos.Filters.newBuilder().setRefuseSeconds(1).build();
                    schedulerDriver.launchTasks(Collections.singleton(offer.getId()), Collections.singletonList(task), filters);
                    return;
                }
            }
            schedulerDriver.declineOffer(offer.getId());
        }
    }

    private Protos.TaskInfo.Builder setResoursesRequirements(Protos.TaskInfo.Builder builder) {
        Protos.TaskInfo.Builder b = builder;
        for (Map.Entry<String, Double> e: requiredResources.entrySet()) {
            b = b.addResources(Protos.Resource.newBuilder()
                    .setName(e.getKey())
                    .setType(Protos.Value.Type.SCALAR)
                    .setScalar(Protos.Value.Scalar.newBuilder().setValue(e.getValue())));

        }
        return b;
    }

    @Override
    public void offerRescinded(SchedulerDriver schedulerDriver, Protos.OfferID offerID) {
        log.info("offerRescinded " + offerID.getValue());
    }

    @Override
    public void statusUpdate(SchedulerDriver schedulerDriver, Protos.TaskStatus taskStatus) {
        log.info("statusUpdate " + taskStatus.getMessage());
    }

    @Override
    public void frameworkMessage(SchedulerDriver schedulerDriver, Protos.ExecutorID executorID, Protos.SlaveID slaveID, byte[] bytes) {
        log.info("frameworkMessage from " + slaveID.getValue());
    }

    @Override
    public void disconnected(SchedulerDriver schedulerDriver) {
        System.out.println("Disconnected from master");
    }

    @Override
    public void slaveLost(SchedulerDriver schedulerDriver, Protos.SlaveID slaveID) {

    }

    @Override
    public void executorLost(SchedulerDriver schedulerDriver, Protos.ExecutorID executorID, Protos.SlaveID slaveID, int i) {

    }

    @Override
    public void error(SchedulerDriver schedulerDriver, String s) {
        System.out.println("ERROR from master, " + s);
    }
}
