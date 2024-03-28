package com.sample.single.application.controler;

import com.lrenyi.oauth2.service.TemplateOauthService;
import com.lrenyi.template.core.util.OAuth2Constant;
import com.lrenyi.template.core.util.Result;
import com.lrenyi.template.core.util.TokenBean;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthCodeController {
    
    private TemplateOauthService templateOauthService;
    
    @Autowired
    public void setTemplateOauthService(TemplateOauthService templateOauthService) {
        this.templateOauthService = templateOauthService;
    }
    
    @GetMapping("/callback/oauth2/code")
    public Result<TokenBean> requestOauthCodeRedirect(HttpServletRequest request) {
        String code = request.getParameter("code");
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        
        body.put(OAuth2Constant.CLIENT_ID_FIELD_KEY, Collections.singletonList("default-client-id"));
        body.put(OAuth2Constant.CLIENT_SECRET_FIELD_KEY, Collections.singletonList("app.template"));
        body.put(OAuth2Constant.AUTHORIZATION_CODE_KEY, Collections.singletonList(code));
        body.put(OAuth2Constant.REDIRECT_URI_KEY,
                 Collections.singletonList("http://127.0.0.1:8080/callback/oauth2/code")
        );
        String codeValue = AuthorizationGrantType.AUTHORIZATION_CODE.getValue();
        body.put(OAuth2Constant.GRANT_TYPE_FIELD_KEY, Collections.singletonList(codeValue));
        
        return templateOauthService.login(body, header);
    }
}
