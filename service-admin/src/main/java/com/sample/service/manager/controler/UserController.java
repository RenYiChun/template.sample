package com.sample.service.manager.controler;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.service.ISystemUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private ISystemUserService systemUserService;
    
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody SysUser sysUser) throws Throwable {
        systemUserService.saveOrUpdate(sysUser);
        return Result.getSuccess("ok");
    }
    
    @GetMapping("/name/token/{token}")
    public Result<String> findUserNameByToken(@PathVariable String token) throws Throwable {
        String userName = systemUserService.findUserNameByToken(token);
        return Result.getSuccess(userName);
    }
}
