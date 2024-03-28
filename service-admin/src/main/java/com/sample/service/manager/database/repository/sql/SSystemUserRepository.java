package com.sample.service.manager.database.repository.sql;

import com.sample.service.manager.database.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SSystemUserRepository extends JpaRepository<SysUser, String> {
    SysUser findSysUsersByUserName(String userName);
    
    SysUser findSysUserByJobNumber(String jobNumber);
}
