package com.nzj.myconsulclient;

import com.nzj.myconsulclient.feignclient.MyConsulClientAFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@RestController
@SpringBootApplication
public class MyConsulClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyConsulClientAFeign myConsulClientAFeign;

    @RequestMapping("/")
    public String home() {
        // 使用RestTemplate调用远程服务
        String s = restTemplate.getForObject("http://my-consul-client-a/", String.class);
        return "获取到远程服务my-consul-client-a的结果: " + s;
    }

    /**
     * 使用openfeign声明式服务调用简化代码
     * openfeign客户端自带loadbalancer负载均衡
     */
    @RequestMapping("/home2")
    public String home2() {
        // 使用RestTemplate调用远程服务
        String s = myConsulClientAFeign.home();
        return "使用feign client获取到远程服务my-consul-client-a的结果: " + s;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyConsulClientApplication.class, args);
    }


}
