package com.sample.service.manager.service.impl;

import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.service.ISystemUserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService implements ISystemUserService {
    
    private OAuth2AuthorizationService oAuth2AuthorizationService;
    private SysUser sysUser;
    
    @Autowired
    public void setoAuth2AuthorizationService(OAuth2AuthorizationService oAuth2AuthorizationService) {
        this.oAuth2AuthorizationService = oAuth2AuthorizationService;
    }
    
    @Override
    public void saveOrUpdate(SysUser sysUser) {
        this.sysUser = sysUser;
    }
    
    @Override
    public List<? extends SysUser> findAll() {
        return Collections.singletonList(sysUser);
    }
    
    @Override
    public String findUserNameByToken(String token) {
        String userName = null;
        OAuth2Authorization authorization = oAuth2AuthorizationService.findByToken(token, null);
        if (authorization != null) {
            userName = authorization.getAttribute("username");
        }
        return userName;
    }
}
