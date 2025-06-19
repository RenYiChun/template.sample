package com.sample.service.dp.controller;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.UserInfoService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFeignController {
    
    @Resource
    private UserInfoService userInfoService;
    
    @GetMapping(value = "/user/query/all/without/login")
    public Result<String> findDictionaryByKeyNotLogin() {
        return userInfoService.findUserNameWithoutToken();
    }
    
    @GetMapping("/user/name/token/{token}")
    Result<String> findUserNameByToken(@PathVariable("token") String token) {
        return userInfoService.findUserNameByToken(token);
    }
}
