package com.sample.service.manager.config;

import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class ServiceManagerConfig {
    
    @Bean
    public Consumer<HttpSecurity> customHttpSecurity() {
        return httpSecurity -> {
            try {
                httpSecurity.sessionManagement((session) -> session.sessionCreationPolicy(
                                                                   SessionCreationPolicy.STATELESS)
                                                           .maximumSessions(10)
                                                           .maxSessionsPreventsLogin(true));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
