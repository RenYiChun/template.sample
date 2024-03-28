package com.sample.service.manager.database.repository.mongo;

import com.sample.service.manager.database.entity.Dictionary;
import com.sample.service.manager.database.repository.IDictionaryRepository;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryRepository implements IDictionaryRepository {
    
    @Resource
    private SDictionaryRepository sDictionaryRepository;
    
    @Override
    public Dictionary findDictionaryByKey(String key) {
        return sDictionaryRepository.findDictionaryByKey(key);
    }
    
    @Override
    public int count() {
        return sDictionaryRepository.findAll().size();
    }
    
    @Override
    public void saveOrUpdate(Dictionary dictionary) {
        sDictionaryRepository.save(dictionary);
    }
    
    @Override
    public List<Dictionary> findAllDictionary() {
        return sDictionaryRepository.findAll();
    }
}
