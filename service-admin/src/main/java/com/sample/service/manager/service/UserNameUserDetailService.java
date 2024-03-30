package com.sample.service.manager.service;

import com.lrenyi.oauth2.service.oauth2.password.LoginNameType;
import com.lrenyi.oauth2.service.oauth2.password.LoginNameUserDetailService;
import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.database.repository.ISystemUserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserNameUserDetailService implements LoginNameUserDetailService {
    @Value(
            "${app.system.admin"
                    + ".default-password:{default"
                    + "}bbc3996c64ae76003d6c034cb3fa7a3e51dc624ee097681e39ec8f42}"
    )
    private String defaultPassword;
    
    @Resource
    private ISystemUserRepository systemUserRepository;
    
    @Override
    public String loginNameType() {
        return LoginNameType.USER_NAME.getCode();
    }
    
    @Override
    public UserDetails loadUserDetail(String code) throws UsernameNotFoundException {
        SysUser sysUser = systemUserRepository.findSysUsersByUserName(code);
        if (sysUser == null && "admin".equals(code)) {
            sysUser = new SysUser();
            sysUser.setUserName("admin");
            sysUser.setCreateBy("system");
            sysUser.setUpdateBy("system");
            sysUser.setRemark("created at init");
            sysUser.setPassword(defaultPassword);
            systemUserRepository.saveOrUpdate(sysUser);
        }
        if (sysUser == null) {
            return null;
        }
        return User.builder()
                   .username(sysUser.getUserName())
                   .password(sysUser.getPassword())
                   .accountLocked(sysUser.isLocked())
                   .disabled(sysUser.isDisabled())
                   .accountExpired(sysUser.isExpired())
                   .build();
    }
}
