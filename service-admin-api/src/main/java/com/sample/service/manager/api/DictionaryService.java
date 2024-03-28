package com.sample.service.manager.api;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.fallback.DictionaryServiceFallback;
import com.sample.service.manager.api.vo.DictionaryVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "admin", contextId = "dictionary", fallbackFactory = DictionaryServiceFallback.class
)
public interface DictionaryService {
    
    @GetMapping(value = "/dict/get/{key}")
    Result<DictionaryVo> findDictionaryByKey(@PathVariable String key);
    
    @GetMapping(value = "/dict/query/all")
    Result<List<DictionaryVo>> findDictionaryAll();
}
