package com.sample.service.gateway.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;

public class BeforeBootstrapInitializer implements BootstrapRegistryInitializer {
    @Override
    public void initialize(BootstrapRegistry registry) {
        //一下对应用中使用的JSON做一些全局配置
        JSON.config(JSONWriter.Feature.WriteMapNullValue);
    }
}
