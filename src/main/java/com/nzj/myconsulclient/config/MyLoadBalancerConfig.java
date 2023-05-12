package com.nzj.myconsulclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置随机负载策略
 *
 * @Author niuzheju
 * @Date 16:39 2023/5/8
 */
@Configuration
public class MyLoadBalancerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyLoadBalancerConfig.class);

    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
        LOGGER.info("Configuring Load balancer to prefer same instance");
        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                .withSameInstancePreference()
                .build(context);
    }
}
