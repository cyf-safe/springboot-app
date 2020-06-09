package com.iokbl.config.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;

@Slf4j
@RibbonClients(value = {
       @RibbonClient(name = "springboot-service",configuration = PlanMainServiceRibbonConfig.class),
       @RibbonClient(name = "springboot-other-service",configuration = PlanOtherServiceRibbonConfig.class)
})
public class CustomRibbonConfig {

}

class PlanMainServiceRibbonConfig {

    @Bean
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
}

class PlanOtherServiceRibbonConfig {

    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }
}
