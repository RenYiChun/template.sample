package com.sample.service.manager.service.impl;

import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import com.sample.service.manager.database.entity.SysUser;
import com.sample.service.manager.database.repository.ISystemUserRepository;
import com.sample.service.manager.service.ISystemUserService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService implements ISystemUserService {
    
    @Resource
    private ISystemUserRepository systemUserRepository;
    @Value(
            "${app.system.admin" +
                    ".default-password:{default}bbc3996c64ae76003d6c034cb3fa7a3e51dc624ee097681e39ec8f42}"
    )
    private String defaultPassword;
    
    private OAuth2AuthorizationService oAuth2AuthorizationService;
    
    @Autowired
    public void setoAuth2AuthorizationService(OAuth2AuthorizationService oAuth2AuthorizationService) {
        this.oAuth2AuthorizationService = oAuth2AuthorizationService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String userName) {
        SysUser sysUser = systemUserRepository.findSysUsersByUserName(userName);
        if (sysUser == null && "admin".equals(userName)) {
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
    
    @Override
    public UserDetails loadUserByJobNumber(String jobNumber) {
        SysUser sysUser = systemUserRepository.findSysUserByJobNumber(jobNumber);
        return User.builder()
                   .username(sysUser.getUserName())
                   .password(sysUser.getPassword())
                   .accountLocked(sysUser.isLocked())
                   .disabled(sysUser.isDisabled())
                   .accountExpired(sysUser.isExpired())
                   .build();
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
