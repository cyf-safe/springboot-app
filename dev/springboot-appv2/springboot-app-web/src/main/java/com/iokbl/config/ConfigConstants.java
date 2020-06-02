package com.iokbl.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigConstants {

    @Value("${login.timeout.minutes}")
    private int loginTimeoutMinutes;

    private String serviceName = "springboot-service";

    public int getLoginTimeoutMinutes() {
        return loginTimeoutMinutes;
    }

    public void setLoginTimeoutMinutes(int loginTimeoutMinutes) {
        this.loginTimeoutMinutes = loginTimeoutMinutes;
    }

    public String getServiceName() {
        return serviceName;
    }

}
