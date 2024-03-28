package com.sample.service.gateway.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(SecurityConfigProperties.PREFIX)
public class SecurityConfigProperties {
    public static final String PREFIX = "sample.security";
    
    private List<String> mustHaveHeader = new ArrayList<>();
}
