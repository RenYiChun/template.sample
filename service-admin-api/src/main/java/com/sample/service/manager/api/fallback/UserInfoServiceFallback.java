package com.sample.service.manager.api.fallback;

import com.sample.service.manager.api.DictionaryService;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserInfoServiceFallback implements FallbackFactory<DictionaryService> {
    @Override
    public DictionaryService create(Throwable cause) {
        return null;
    }
}
