package com.sample.service.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.lrenyi.template.core.config.properties.CustomSecurityConfigProperties;
import com.lrenyi.template.core.config.redis.TemplateRedisTemplate;
import com.lrenyi.template.core.util.MCode;
import com.lrenyi.template.core.util.Result;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Resource
    private TemplateRedisTemplate templateRedisTemplate;
    @Resource
    private CustomSecurityConfigProperties customSecurityConfigProperties;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String ignore = serverHttpRequest.getHeaders().getFirst("IgnorePath");
        if (Boolean.parseBoolean(ignore)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        String header = serverHttpRequest.getHeaders().getFirst("Authorization");
        assert header != null;
        String token = header.split(" ")[1];
        String key = "access_token:" + token;
        String userName = templateRedisTemplate.opsForValue().get(key);
        if (!StringUtils.hasLength(userName) && customSecurityConfigProperties.isAutoRedirectLoginPage()) {
            String loginPageUrl = customSecurityConfigProperties.getRedirectLoginPageUrl();
            if (StringUtils.hasLength(loginPageUrl)) {
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, loginPageUrl);
                return response.setComplete();
            }
        } else {
            ServerHttpRequest.Builder newHeader = exchange.getRequest().mutate().header("username", userName);
            return chain.filter(exchange.mutate().request(newHeader.build()).build());
        }
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        Result<Object> result = new Result<>("session timeout");
        result.setCode(MCode.NO_PERMISSIONS.getCode());
        byte[] bytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
