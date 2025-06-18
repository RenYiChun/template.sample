package com.sample.service.manager.service;

import com.sample.service.manager.database.entity.SysUser;
import java.util.List;

public interface ISystemUserService {
    
    void saveOrUpdate(SysUser sysUser);
    
    List<? extends SysUser> findAll();
    
    String findUserNameByToken(String token);
}
