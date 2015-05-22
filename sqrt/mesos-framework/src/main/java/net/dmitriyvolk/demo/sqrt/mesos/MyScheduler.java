package net.dmitriyvolk.demo.sqrt.mesos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mesos.Protos;
import org.apache.mesos.Scheduler;
import org.apache.mesos.SchedulerDriver;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyScheduler implements Scheduler {

    private Log log = LogFactory.getLog(this.getClass());

    private final HasMoreChecker checker;

    /** Task ID generator. */
    private final AtomicInteger taskIDGenerator = new AtomicInteger();

    public MyScheduler(HasMoreChecker checker) {
        this.checker = checker;
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
                // generate a unique task ID
                Protos.TaskID taskId = Protos.TaskID.newBuilder()
                        .setValue(Integer.toString(taskIDGenerator.incrementAndGet())).build();
                log.info("Launching task " + taskId.getValue());

                Protos.ContainerInfo.Builder containerBuilder = Protos.ContainerInfo.newBuilder()
                        .setType(Protos.ContainerInfo.Type.DOCKER)
                        .setDocker(Protos.ContainerInfo.DockerInfo.newBuilder()
                                .setImage("artifactory-dvolk.eng.vmware.com:5000/dvolk/sqrt-client")
                                .setNetwork(Protos.ContainerInfo.DockerInfo.Network.valueOf("HOST"))
                                .build());

                Protos.TaskInfo task = Protos.TaskInfo.newBuilder()
                        .setTaskId(taskId)
                        .setName("sqrt-task-" + taskId.getValue())
                        .setSlaveId(offer.getSlaveId())
                        .addResources(Protos.Resource.newBuilder()
                                .setName("cpus")
                                .setType(Protos.Value.Type.SCALAR)
                                .setScalar(Protos.Value.Scalar.newBuilder().setValue(2)))
                        .addResources(Protos.Resource.newBuilder()
                                .setName("mem")
                                .setType(Protos.Value.Type.SCALAR)
                                .setScalar(Protos.Value.Scalar.newBuilder().setValue(512)))
                        .setContainer(containerBuilder)
                        .setCommand(Protos.CommandInfo.newBuilder().setShell(false))
                        .build();

                Protos.Filters filters = Protos.Filters.newBuilder().setRefuseSeconds(1).build();
                schedulerDriver.launchTasks(Collections.singleton(offer.getId()), Collections.singletonList(task), filters);
                return;
            }
            schedulerDriver.declineOffer(offer.getId());
        }
    }

    @Override
    public void offerRescinded(SchedulerDriver schedulerDriver, Protos.OfferID offerID) {

    }

    @Override
    public void statusUpdate(SchedulerDriver schedulerDriver, Protos.TaskStatus taskStatus) {
    }

    @Override
    public void frameworkMessage(SchedulerDriver schedulerDriver, Protos.ExecutorID executorID, Protos.SlaveID slaveID, byte[] bytes) {

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
