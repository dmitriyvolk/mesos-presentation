package net.dmitriyvolk.demo.sqrt.client;

import net.dmitriyvolk.demo.sqrt.server.domain.WorkItem;
import net.dmitriyvolk.demo.sqrt.server.domain.WorkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SqrtClient {

    @Autowired
    DiscoveryClient discoveryClient;

    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        WorkItem wi = restTemplate.getForObject(getSqrtServerUrl() + "/workitems/next", WorkItem.class);
        if (wi == null) {
            System.out.println("No available work items! Exiting");
            return;
        }
        restTemplate.postForLocation(getSqrtServerUrl() + "/workitems/result", produceResult(wi));
    }

    private String getSqrtServerUrl() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("sqrt-server").get(0);

        return String.format("http://%s:%d", serviceInstance.getHost(), serviceInstance.getPort());
    }

    private WorkResult produceResult(WorkItem wi) {
        float oldApproximation = wi.getCurrentApproximation();
        long square = wi.getSquare();
        WorkResult workResult = new WorkResult(square, (oldApproximation + square / oldApproximation) / 2, wi.getIterationNumber());
        System.out.println("Submitting work result: " + workResult);
        return workResult;
    }
}
