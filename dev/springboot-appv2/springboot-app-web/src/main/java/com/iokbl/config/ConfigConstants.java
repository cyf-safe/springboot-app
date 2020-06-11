package com.iokbl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ConfigConstants {

    @Value("${login.timeout.minutes}")
    private int loginTimeoutMinutes;

    @Value("${springboot-service-appid}")
    private String serviceName;

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
