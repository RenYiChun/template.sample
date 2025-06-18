package com.sample.service.manager.service;

import com.lrenyi.oauth2.service.oauth2.password.LoginNameType;
import com.lrenyi.oauth2.service.oauth2.password.LoginNameUserDetailService;
import com.sample.service.manager.database.entity.SysUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JobNumberUserDetailService implements LoginNameUserDetailService {
    
    @Override
    public String loginNameType() {
        return LoginNameType.JOB_NUMBER.getCode();
    }
    
    @Override
    public UserDetails loadUserDetail(String code) throws AuthenticationException {
        SysUser sysUser = new SysUser();
        return User.builder()
                   .username(sysUser.getUserName())
                   .password(sysUser.getPassword())
                   .accountLocked(sysUser.isLocked())
                   .disabled(sysUser.isDisabled())
                   .accountExpired(sysUser.isExpired())
                   .build();
    }
}
