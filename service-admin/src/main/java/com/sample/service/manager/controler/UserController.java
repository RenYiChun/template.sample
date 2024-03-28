package com.sample.service.manager.controler;

import com.lrenyi.template.core.util.Result;
import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import com.lrenyi.template.web.function.Log;
import com.lrenyi.template.web.function.log.OperationEnum;
import com.lrenyi.template.web.function.log.OperationObject;
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
    @Log(object = OperationObject.USER_MANAGER, operation = OperationEnum.ADD)
    public Result<String> addUser(@RequestBody SysUser sysUser) throws Throwable {
        systemUserService.saveOrUpdate(sysUser);
        return PageResult.getSuccess("ok");
    }
    
    @GetMapping("/name/token/{token}")
    public Result<String> findUserNameByToken(@PathVariable String token) throws Throwable {
        String userName = systemUserService.findUserNameByToken(token);
        return PageResult.getSuccess(userName);
    }
    
    @PostMapping("/query/page")
    @Log(object = OperationObject.USER_MANAGER, operation = OperationEnum.QUERY)
    public PageResult<?> querySysUserInfo(@RequestBody PagerConditions<SysUser> query) {
        return systemUserService.querySysUserInfo(query);
    }
}
