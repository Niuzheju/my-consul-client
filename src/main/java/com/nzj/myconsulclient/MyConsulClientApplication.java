package com.nzj.myconsulclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class MyConsulClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String home() {
        // 使用RestTemplate调用远程服务
        String s = restTemplate.getForObject("http://my-consul-client-a/", String.class);
        return "获取到远程服务my-consul-client-a的结果: " + s;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyConsulClientApplication.class, args);
    }


}
