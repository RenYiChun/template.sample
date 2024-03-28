package com.sample.service.manager.database.repository.mongo;

import com.sample.service.manager.database.entity.OperateLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SOperateLogRepository extends MongoRepository<OperateLog, String> {}
