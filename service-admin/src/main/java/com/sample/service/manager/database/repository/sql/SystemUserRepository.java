package com.sample.service.manager.database.repository.sql;

import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.database.repository.ISystemUserRepository;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class SystemUserRepository implements ISystemUserRepository {
    
    @Resource
    private SSystemUserRepository customMongoRepository;
    
    @Override
    public SysUser findSysUsersByUserName(String userName) {
        return customMongoRepository.findSysUsersByUserName(userName);
    }
    
    @Override
    public SysUser findSysUserByJobNumber(String jobNumber) {
        return customMongoRepository.findSysUserByJobNumber(jobNumber);
    }
    
    @Override
    public void saveOrUpdate(SysUser sysUser) {
        customMongoRepository.save(sysUser);
    }
    
    @Override
    public List<? extends SysUser> findAll() {
        return customMongoRepository.findAll();
    }
    
    @Override
    public Page<SysUser> querySysUserInfo(PagerConditions<SysUser> query) {
        return customMongoRepository.findAll(query);
    }
}
