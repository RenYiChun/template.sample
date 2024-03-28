package com.sample.single.application.oauth;

import com.lrenyi.oauth2.service.TemplateUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService implements TemplateUserService {
    
    private static final String testPassword = """
            @#*H17RSA2048kBNEf9MtwgiGISj/atKYLt2P/R/YHgFS47EU/FjO22B9VLnCgGhw8kM3+3p8QDbihtyvJlU2ti2R0xIuTSKaEmV3AHgR7RPKt7f7JlRtelTk+1IjZ2qVO8p4Sl0XkPa2uGuR/8ClrFk34+A22om9Ik5FHe5YmWnZQ09Djo9Jwp93kqhLeCmiU2bbxVYzLEIyUF3k9/bjrwmV0rnRdiPxy98Blrd2+pO7h6arPz0rwjOpvMRL6W8rQ3FmmW7I/zdnsx/6FqjH0Edm1/5ANJEswRa/7v/nFd5gjexdr//0ExNJCpRIyFFIgmBsU+FCLTay7m7hHpNwY/K3L8GOo8WoMA==@#*H
            """;
    
    @Override
    public UserDetails loadUserByUsername(String userName) {
        return User.builder().username("admin").password(testPassword).build();
    }
    
    @Override
    public UserDetails loadUserByJobNumber(String jobNumber) {
        return User.builder().username("admin").password(testPassword).build();
    }
}
