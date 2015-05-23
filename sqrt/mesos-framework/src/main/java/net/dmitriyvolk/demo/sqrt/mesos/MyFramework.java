package net.dmitriyvolk.demo.sqrt.mesos;

import org.apache.mesos.MesosSchedulerDriver;
import org.apache.mesos.Protos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class MyFramework {

    @Value("${mesosMaster}")
    String masterHostAndPort;

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${imageName}")
    String imageName;


    public void start() {
        Protos.FrameworkInfo.Builder frameworkBuilder = Protos.FrameworkInfo.newBuilder()
                .setName("SqrtFramework")
                .setUser("")
                .setFailoverTimeout(0);


        MesosSchedulerDriver driver = new MesosSchedulerDriver(new MyScheduler(new RestHasMoreChecker(getSqrtServerUrl()), imageName), frameworkBuilder.build(), masterHostAndPort);

        System.err.println("Exit status: " + driver.run());

        driver.stop();
    }

    private String getSqrtServerUrl() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("sqrt-server").get(0);
        return String.format("http://%s:%d",serviceInstance.getHost(), serviceInstance.getPort());
    }

}
