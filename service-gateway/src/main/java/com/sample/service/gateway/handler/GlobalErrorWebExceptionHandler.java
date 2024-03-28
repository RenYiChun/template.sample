package com.sample.service.gateway.handler;

import com.alibaba.fastjson2.JSON;
import com.lrenyi.template.core.util.Result;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-2)
@Component
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable cause) {
        log.error("发生了未被处理的异常.", cause);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        
        Result<String> error = new Result<>();
        error.makeThrowable(cause, "an internal manager exception occurred at the gateway");
        error.setData(request.getPath().value());
        byte[] bytes = JSON.toJSONString(error).getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
