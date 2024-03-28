package com.sample.service.manager.config;

import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class ServiceManagerConfig {
    
    @Bean
    public Consumer<HttpSecurity> customHttpSecurity() {
        return httpSecurity -> {};
    }
}
