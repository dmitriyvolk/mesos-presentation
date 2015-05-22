package net.dmitriyvolk.demo.sqrt.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableEurekaClient
public class SqrtClientMain {
    public static void main(String[] args) {
        SpringApplication.run(SqrtClientMain.class, args).getBean(SqrtClient.class).run();

    }
}
