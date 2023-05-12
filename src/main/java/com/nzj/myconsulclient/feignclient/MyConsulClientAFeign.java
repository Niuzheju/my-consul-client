package com.nzj.myconsulclient.feignclient;

import com.nzj.myconsulclient.config.MyLoadBalancerConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author niuzheju
 * @Date 16:11 2023/5/8
 */
@LoadBalancerClient(name = "my-consul-client-a", configuration = MyLoadBalancerConfig.class)
@FeignClient(value = "my-consul-client-a")
@CircuitBreaker(name = "my-consul-client-a", fallbackMethod = "defaultFallback")
public interface MyConsulClientAFeign {

    Logger LOGGER = LoggerFactory.getLogger(MyConsulClientAFeign.class);

    @GetMapping("/")
    String home();

    @GetMapping("/getException")
    String getException();

    default String defaultFallback(Throwable throwable) {
        LOGGER.error("调用远程服务失败, cause: ", throwable);
        return "default fallback";
    }

}
