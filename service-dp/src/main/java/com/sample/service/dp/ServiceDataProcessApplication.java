package com.sample.service.dp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.sample.**.api"})
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceDataProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDataProcessApplication.class, args);
    }
}
