package com.sample.service.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.lrenyi.template.core.config.properties.CustomSecurityConfigProperties;
import com.lrenyi.template.core.util.MCode;
import com.lrenyi.template.core.util.Result;
import com.sample.service.gateway.config.SecurityConfigProperties;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestHeaderFilter implements GlobalFilter, Ordered {
    private final AntPathMatcher matcher = new AntPathMatcher("/");
    @Resource
    private CustomSecurityConfigProperties customSecurityConfigProperties;
    @Resource
    private SecurityConfigProperties securityConfigProperties;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        
        RequestPath path = request.getPath();
        String realPath = path.value();
        realPath = findRealPath(realPath);
        Set<String> allPermitUrls = customSecurityConfigProperties.getAllPermitUrls();
        boolean match = false;
        for (String s : allPermitUrls) {
            match = matcher.match(s.trim(), realPath);
            if (match) {
                break;
            }
        }
        ServerHttpRequest.Builder newHeader;
        if (match) {
            newHeader = exchange.getRequest().mutate().header("IgnorePath", String.valueOf(true));
            return chain.filter(exchange.mutate().request(newHeader.build()).build());
        }
        HttpHeaders headers = request.getHeaders();
        List<String> haveHeader = securityConfigProperties.getMustHaveHeader();
        for (String header : haveHeader) {
            List<String> list = headers.get(header);
            if (list == null || list.isEmpty()) {
                if ("Authorization".equals(header)) {
                    String redirectLoginPage =
                            customSecurityConfigProperties.getRedirectLoginPageUrl();
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, redirectLoginPage);
                    return response.setComplete();
                }
                Result<?> result = new Result<>();
                result.setCode(MCode.NO_PERMISSIONS.getCode());
                result.setMessage("请求参数异常，请检查后再重试");
                byte[] bytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
            }
        }
        newHeader = exchange.getRequest().mutate().header("IgnorePath", String.valueOf(false));
        return chain.filter(exchange.mutate().request(newHeader.build()).build());
    }
    
    private String findRealPath(String path) {
        String substring = path.substring(1);
        int start = substring.indexOf("/");
        return substring.substring(start);
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
