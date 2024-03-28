package com.sample.service.manager.database.repository;

import com.sample.service.manager.database.entity.SysUser;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ISystemUserRepository {
    SysUser findSysUsersByUserName(String userName);
    
    SysUser findSysUserByJobNumber(String jobNumber);
    
    void saveOrUpdate(SysUser sysUser);
    
    List<? extends SysUser> findAll();
    
    Page<SysUser> querySysUserInfo(PagerConditions<SysUser> query);
}
