package com.iokbl.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DemoServicesFeignConfig {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
        //return Logger.Level.HEADERS;
        //return Logger.Level.BASIC;
        //return Logger.Level.NONE;
    }

}
