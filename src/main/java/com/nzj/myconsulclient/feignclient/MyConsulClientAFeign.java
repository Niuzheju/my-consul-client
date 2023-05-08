package com.nzj.myconsulclient.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author niuzheju
 * @Date 16:11 2023/5/8
 */
@FeignClient("my-consul-client-a")
public interface MyConsulClientAFeign {

    @GetMapping("/")
    String home();

}
