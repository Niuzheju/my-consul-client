package com.nzj.myconsulclient;

import com.nzj.myconsulclient.config.MyLoadBalancerConfig;
import com.nzj.myconsulclient.feignclient.MyConsulClientAFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RefreshScope // 用于自动刷新分布式配置属性，需要写在属性所在的类
@LoadBalancerClient(name = "my-consul-client", configuration = MyLoadBalancerConfig.class)
@EnableFeignClients
@RestController
@SpringBootApplication
public class MyConsulClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyConsulClientAFeign myConsulClientAFeign;

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

    public static void main(String[] args) {
        SpringApplication.run(MyConsulClientApplication.class, args);
    }


}
