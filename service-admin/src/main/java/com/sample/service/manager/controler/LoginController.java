package com.sample.service.manager.controler;

import com.lrenyi.oauth2.service.TemplateOauthService;
import com.lrenyi.template.core.config.redis.TemplateRedisTemplate;
import com.lrenyi.template.core.util.OAuth2Constant;
import com.lrenyi.template.core.util.Result;
import com.lrenyi.template.web.function.Log;
import com.lrenyi.template.web.function.log.OperationEnum;
import com.lrenyi.template.web.function.log.OperationObject;
import com.sample.service.manager.util.VerifyCodeUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
public class LoginController {
    @Resource
    private TemplateRedisTemplate templateRedisTemplate;
    @Resource
    private TemplateOauthService templateOauthService;
    
    @GetMapping(value = "/")
    public void index(HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write("index page".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception ignore) {}
    }
    
    @ResponseBody
    @RequestMapping(value = "/getCode")
    public Result<Object> generateCheckCode(HttpServletRequest request) {
        String id = request.getHeader("clientId");
        String key = "code:" + id;
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4, "");
        //通过redis 控制验证码超时
        templateRedisTemplate.opsForValue().set(key, verifyCode);
        templateRedisTemplate.expire(key, 2, TimeUnit.MINUTES);
        //生成图片
        int w = 100, h = 44;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            VerifyCodeUtils.outputImage(w, h, baos, verifyCode);
        } catch (IOException e) {
            String msg = String.format("生成校验码图片时发送异常：%s", e.getMessage());
            log.error(msg, e);
            Result<Object> err = new Result<>();
            err.makeThrowable(e, msg);
            return err;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String s1 = encoder.encodeToString(baos.toByteArray());
        Result<Object> ok = Result.getSuccess("ok");
        ok.setData(s1);
        return ok;
    }
    
    @PostMapping("/credentials/login")
    @Log(object = OperationObject.LOGIN_LOGOUT, operation = OperationEnum.LOGIN)
    public Result<?> devLogin() {
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.put(OAuth2Constant.CLIENT_ID_FIELD_KEY,
                 Collections.singletonList("default-client-id")
        );
        body.put(OAuth2Constant.CLIENT_SECRET_FIELD_KEY, Collections.singletonList("app.template"));
        String value = AuthorizationGrantType.CLIENT_CREDENTIALS.getValue();
        body.put(OAuth2Constant.GRANT_TYPE_FIELD_KEY, Collections.singletonList(value));
        body.put("test", Collections.singletonList("test"));
        
        return templateOauthService.login(body, header);
    }
    
    @GetMapping("/logout")
    @Log(object = OperationObject.LOGIN_LOGOUT, operation = OperationEnum.LOGIN)
    public Result<?> logout(HttpServletRequest request) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", request.getHeader("Authorization"));
        return templateOauthService.logout("http", header);
    }
    
    @PostMapping("/user/login")
    @Log(object = OperationObject.LOGIN_LOGOUT, operation = OperationEnum.LOGIN)
    public Result<?> login(@RequestBody Map<String, String> parameters) {
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        
        String username = parameters.get("username");
        String password = parameters.get("password");
        body.put("username", Collections.singletonList(username));
        body.put("password", Collections.singletonList(password));
        body.put(OAuth2Constant.CLIENT_ID_FIELD_KEY,
                 Collections.singletonList("default-client-id")
        );
        body.put(OAuth2Constant.CLIENT_SECRET_FIELD_KEY, Collections.singletonList("app.template"));
        body.put(OAuth2Constant.GRANT_TYPE_FIELD_KEY,
                 Collections.singletonList(OAuth2Constant.GRANT_TYPE_PASSWORD)
        );
        
        return templateOauthService.login(body, header);
    }
}
