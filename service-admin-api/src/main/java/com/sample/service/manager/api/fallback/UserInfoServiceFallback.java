package com.sample.service.manager.api.fallback;

import com.sample.service.manager.api.UserInfoService;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserInfoServiceFallback implements FallbackFactory<UserInfoService> {
    
    @Override
    public UserInfoService create(Throwable cause) {
        return null;
    }
}
