package com.sample.service.manager.api;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.fallback.UserInfoServiceFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        value = "admin", contextId = "userInfo", fallbackFactory = UserInfoServiceFallback.class,
        url = "http://localhost:8081"
)
public interface UserInfoService {
    
    @PostMapping("/user/query")
    Result<List<?>> queryUserInfo() throws Throwable;
    
    @GetMapping("/user/name/token/{token}")
    Result<String> findUserNameByToken(@PathVariable("token") String token);
    
    @GetMapping("/user/query/all/without/login")
    Result<String> findUserNameWithoutToken();
}
