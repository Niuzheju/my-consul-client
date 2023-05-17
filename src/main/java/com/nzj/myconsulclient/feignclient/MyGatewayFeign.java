package com.nzj.myconsulclient.feignclient;

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
@LoadBalancerClient(name = "my-gateway")
@FeignClient(value = "my-gateway", path = "/my-consul-client-a")
@CircuitBreaker(name = "my-gateway", fallbackMethod = "defaultFallback")
public interface MyGatewayFeign {

    Logger LOGGER = LoggerFactory.getLogger(MyGatewayFeign.class);

    @GetMapping("/")
    String home();

    default String defaultFallback(Throwable throwable) {
        LOGGER.error("调用远程服务失败, cause: ", throwable);
        return "default fallback";
    }

}
