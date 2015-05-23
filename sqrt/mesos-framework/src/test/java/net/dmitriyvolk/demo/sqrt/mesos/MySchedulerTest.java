package net.dmitriyvolk.demo.sqrt.mesos;

import org.apache.mesos.Protos;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MySchedulerTest {


    Map<String, Double> requiredResources = new HashMap<String, Double>();

    @Before
    public void setUp() {
        requiredResources.put("cpus", 1D);
        requiredResources.put("mem", 1024D);
    }

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[][]{
                {1D, 1024D, true},
                {2D, 2048D, true},
                {1D, 1000D, false},
                {0.9D, 1024D, false},
                {0.9D, 2048D, false},
                {0.5D, 512D, false}
        };
    }

    @Parameterized.Parameter(value = 0)
    public Double cpus;

    @Parameterized.Parameter(value = 1)
    public Double mem;

    @Parameterized.Parameter(value = 2)
    public boolean expectedResult;

    @Test
    public void testEnoughOffered() {
        assertEquals(MyScheduler.isEnoughOffered(makeOffer(), requiredResources), expectedResult);
    }

    private org.apache.mesos.Protos.Offer makeOffer() {
        return Protos.Offer.newBuilder()
                .setId(Protos.OfferID.newBuilder().setValue("offerId"))
                .setFrameworkId(Protos.FrameworkID.newBuilder().setValue("frameworkId"))
                .setSlaveId(Protos.SlaveID.newBuilder().setValue("slaveId"))
                .setHostname("hostname")
                .addResources(Protos.Resource.newBuilder()
                        .setName("cpus")
                        .setType(Protos.Value.Type.SCALAR)
                        .setScalar(Protos.Value.Scalar.newBuilder().setValue(cpus)).build())
                .addResources(Protos.Resource.newBuilder()
                        .setName("mem")
                        .setType(Protos.Value.Type.SCALAR)
                        .setScalar(Protos.Value.Scalar.newBuilder().setValue(mem)).build())
                .build();
    }

}