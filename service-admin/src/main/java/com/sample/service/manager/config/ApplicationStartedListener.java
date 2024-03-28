package com.sample.service.manager.config;

import com.alibaba.fastjson2.JSON;
import com.sample.service.manager.database.entity.Dictionary;
import com.sample.service.manager.service.IDictionaryService;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {
    
    @Value("${app.config.database.init:true}")
    private boolean init;
    
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        if (!init) {
            return;
        }
        org.springframework.core.io.Resource resource = context.getResource("classpath:database.init");
        Map<String, List<String>> allInitData = new HashMap<>();
        try {
            File file = resource.getFile();
            List<String> list = Files.readAllLines(file.toPath());
            String type = null;
            for (String record : list) {
                if (record.trim().startsWith("##")) {
                    type = record.trim().substring(2);
                    continue;
                }
                if (!StringUtils.hasLength(type)) {
                    continue;
                }
                List<String> records = allInitData.computeIfAbsent(type, key -> new ArrayList<>());
                records.add(record);
            }
        } catch (Throwable cause) {
            log.error("", cause);
        }
        allInitData.forEach((key, records) -> {
            if ("dictionary".equals(key)) {
                IDictionaryService dictionaryService = context.getBean(IDictionaryService.class);
                if (dictionaryService.count() == 0) {
                    for (String record : records) {
                        Dictionary dictionary = JSON.parseObject(record, Dictionary.class);
                        LocalDateTime now = LocalDateTime.now();
                        dictionary.setCreateTime(now);
                        dictionary.setUpdateTime(now);
                        dictionary.setUpdateBy("system");
                        dictionary.setCreateBy("system");
                        dictionaryService.saveOrUpdate(dictionary);
                    }
                }
            }
        });
    }
}
