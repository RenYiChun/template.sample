package com.sample.service.manager.api.fallback;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.DictionaryService;
import com.sample.service.manager.api.vo.DictionaryVo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class DictionaryServiceFallback implements FallbackFactory<DictionaryService> {
    
    @Override
    public DictionaryService create(Throwable cause) {
        return new DictionaryService() {
            @Override
            public Result<DictionaryVo> findDictionaryByKey(String key) {
                String message = "调用findDictionaryByKey接口过程中出现异常";
                log.error(message, cause);
                return Result.getError(null, message);
            }
            
            @Override
            public Result<List<DictionaryVo>> findDictionaryAll() {
                String message = "调用findDictionaryAll接口过程中出现异常";
                log.error(message, cause);
                return Result.getError(null, message);
            }
        };
    }
}
