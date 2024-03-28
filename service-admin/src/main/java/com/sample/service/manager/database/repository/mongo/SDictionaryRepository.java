package com.sample.service.manager.database.repository.mongo;

import com.sample.service.manager.database.entity.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SDictionaryRepository extends MongoRepository<Dictionary, String> {
    
    Dictionary findDictionaryByKey(String key);
}
