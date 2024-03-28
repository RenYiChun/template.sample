package com.sample.service.manager.service.impl;

import com.sample.service.manager.database.entity.Dictionary;
import com.sample.service.manager.database.repository.IDictionaryRepository;
import com.sample.service.manager.service.IDictionaryService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService implements IDictionaryService {
    
    @Resource
    private IDictionaryRepository iDictionaryRepository;
    
    @Override
    public Dictionary findDictionaryByKey(String key) {
        return iDictionaryRepository.findDictionaryByKey(key);
    }
    
    @Override
    public int count() {
        return iDictionaryRepository.count();
    }
    
    @Override
    public void saveOrUpdate(Dictionary dictionary) {
        iDictionaryRepository.saveOrUpdate(dictionary);
    }
    
    @Override
    public List<Dictionary> findAllDictionary() {
        return iDictionaryRepository.findAllDictionary();
    }
}
