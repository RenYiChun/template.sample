package com.sample.service.dp.controller;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.UserInfoService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFeignController {
    
    @Resource
    private UserInfoService userInfoService;
    
    @GetMapping(value = "/dict/query/all/without/login")
    public Result<String> findDictionaryByKeyNotLogin() {
        return userInfoService.findUserNameByToken("");
    }
    
    @GetMapping(value = "/dict/query/all")
    public Result<String> findDictionaryByKey() {
        return userInfoService.findUserNameByToken("");
    }
}
