package com.sample.service.manager.service;

import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import com.sample.service.manager.database.entity.SysUser;
import java.util.List;

public interface ISystemUserService {
    
    void saveOrUpdate(SysUser sysUser);
    
    List<? extends SysUser> findAll();
    
    PageResult<SysUser> querySysUserInfo(PagerConditions<SysUser> query);
    
    String findUserNameByToken(String token);
}
