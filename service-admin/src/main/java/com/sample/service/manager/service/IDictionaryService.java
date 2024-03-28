package com.sample.service.manager.service;

import com.sample.service.manager.database.entity.Dictionary;
import java.util.List;

public interface IDictionaryService {
    
    Dictionary findDictionaryByKey(String key);
    
    int count();
    
    void saveOrUpdate(Dictionary dictionaryVo);
    
    List<Dictionary> findAllDictionary();
}
