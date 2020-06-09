package com.iokbl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
