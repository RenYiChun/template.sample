package com.sample.service.manager.service.impl;

import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.database.repository.ISystemUserRepository;
import com.sample.service.manager.service.ISystemUserService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService implements ISystemUserService {
    
    @Resource
    private ISystemUserRepository systemUserRepository;
    
    private OAuth2AuthorizationService oAuth2AuthorizationService;
    
    @Autowired
    public void setoAuth2AuthorizationService(OAuth2AuthorizationService oAuth2AuthorizationService) {
        this.oAuth2AuthorizationService = oAuth2AuthorizationService;
    }
    
    @Override
    public void saveOrUpdate(SysUser sysUser) {
        systemUserRepository.saveOrUpdate(sysUser);
    }
    
    @Override
    public List<? extends SysUser> findAll() {
        return systemUserRepository.findAll();
    }
    
    @Override
    public PageResult<SysUser> querySysUserInfo(PagerConditions<SysUser> query) {
        Page<SysUser> sysUsers = systemUserRepository.querySysUserInfo(query);
        return PageResult.getSuccess(sysUsers);
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
