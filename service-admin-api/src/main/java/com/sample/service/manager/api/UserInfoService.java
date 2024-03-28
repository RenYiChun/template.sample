package com.sample.service.manager.api;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.fallback.UserInfoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "admin", contextId = "userInfo", fallbackFactory = UserInfoServiceFallback.class
)
public interface UserInfoService {
    
    @GetMapping("/user/name/token/{token}")
    Result<String> findUserNameByToken(@PathVariable String token);
}
