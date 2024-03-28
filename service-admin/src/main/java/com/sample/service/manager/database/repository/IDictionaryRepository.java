package com.sample.service.manager.database.repository;

import com.sample.service.manager.database.entity.Dictionary;
import java.util.List;

public interface IDictionaryRepository {
    Dictionary findDictionaryByKey(String key);
    
    int count();
    
    void saveOrUpdate(Dictionary dictionary);
    
    List<Dictionary> findAllDictionary();
}
