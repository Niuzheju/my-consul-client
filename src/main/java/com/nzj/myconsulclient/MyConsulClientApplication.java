package com.nzj.myconsulclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class MyConsulClientApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/")
    public String home() {
        String serviceUrl = serviceUrl();
        System.out.println(serviceUrl);
        return "Hello World";
    }

    public String serviceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("my-consul-client");
        if (instances != null && instances.size() > 0) {
            for (ServiceInstance instance : instances) {
                System.out.println(instance);
            }
            return instances.get(0).getUri().toString();
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyConsulClientApplication.class, args);
    }

}
