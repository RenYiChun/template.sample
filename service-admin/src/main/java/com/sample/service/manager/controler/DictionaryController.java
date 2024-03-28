package com.sample.service.manager.controler;

import com.lrenyi.template.core.util.Result;
import com.lrenyi.template.web.function.Log;
import com.lrenyi.template.web.function.log.OperationEnum;
import com.lrenyi.template.web.function.log.OperationObject;
import com.sample.service.manager.database.entity.Dictionary;
import com.sample.service.manager.service.IDictionaryService;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DictionaryController {
    
    @Resource
    private IDictionaryService iDictionaryService;
    
    @GetMapping(value = "/dict/get/{key}")
    @Log(object = OperationObject.DATA_DIRECTORY, operation = OperationEnum.QUERY)
    public Result<Dictionary> findDictionaryByKey(@PathVariable String key) {
        Dictionary dictionary = iDictionaryService.findDictionaryByKey(key);
        return Result.getSuccess(dictionary);
    }
    
    @GetMapping(value = "/dict/query/all")
    @Log(object = OperationObject.DATA_DIRECTORY, operation = OperationEnum.QUERY)
    public Result<List<Dictionary>> findDictionaryAll() {
        List<Dictionary> dictionaries = iDictionaryService.findAllDictionary();
        return Result.getSuccess(dictionaries);
    }
}
