package com.nzj.myconsulclient;

import com.nzj.myconsulclient.feignclient.MyConsulClientAFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RefreshScope // 用于自动刷新分布式配置属性，需要写在属性所在的类
@EnableFeignClients
@RestController
@SpringBootApplication
public class MyConsulClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyConsulClientAFeign myConsulClientAFeign;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    /**
     * RestTemplate调用远程服务
     */
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

    /**
     * 分布式配置
     */
    @RequestMapping("/home3")
    public String distributeConfig() {
        return "consul config name: " + name + " age: " + age;
    }

    @RequestMapping("/home4")
    public String circuitBreakerInvoke() {
        StringBuilder result = new StringBuilder();
        circuitBreakerFactory.create("slow").
                run(() -> result.append(restTemplate.getForObject("http://my-consul-client-a/", String.class)), throwable -> result.append("fallback"));
        return "手动使用circuit breaker调用远程服务，结果: " + result;
    }

    @RequestMapping("/home5")
    public String circuitBreakerWithFeign() {
        String s = myConsulClientAFeign.getException();
        return "feign client中使用circuit breaker，调用远程服务，结果: " + s;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyConsulClientApplication.class, args);
    }


}
