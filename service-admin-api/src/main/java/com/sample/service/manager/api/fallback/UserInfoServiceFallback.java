package com.sample.service.manager.api.fallback;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.UserInfoService;
import java.util.List;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceFallback implements FallbackFactory<UserInfoService> {
    
    @Override
    public UserInfoService create(Throwable cause) {
        return new UserInfoService() {
            @Override
            public Result<List<?>> queryUserInfo() throws Throwable {
                return Result.getError(null, "Service temporarily unavailable");
            }
            
            @Override
            public Result<String> findUserNameByToken(String token) {
                return Result.getError(null, "Service temporarily unavailable");
            }
            
            @Override
            public Result<String> findUserNameWithoutToken() {
                return Result.getError(null, "Service temporarily unavailable");
            }
        };
    }
}
