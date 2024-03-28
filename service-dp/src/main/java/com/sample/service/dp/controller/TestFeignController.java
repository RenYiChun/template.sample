package com.sample.service.dp.controller;

import com.lrenyi.template.core.util.Result;
import com.sample.service.manager.api.DictionaryService;
import com.sample.service.manager.api.vo.DictionaryVo;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFeignController {
    
    @Resource
    private DictionaryService dictionaryService;
    
    @GetMapping(value = "/dict/query/all")
    public Result<List<DictionaryVo>> findDictionaryByKey() {
        return dictionaryService.findDictionaryAll();
    }
}
