package com.sample.service.manager.database.repository.mongo;

import com.sample.service.manager.database.entity.OperateLog;
import com.sample.service.manager.database.repository.IOperateLogRepository;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OperateLogRepository implements IOperateLogRepository {
    @Resource
    private SOperateLogRepository sOperateLogRepository;
    
    @Override
    public OperateLog saveOrUpdate(OperateLog log) {
        return sOperateLogRepository.save(log);
    }
    
    @Override
    public Page<OperateLog> queryLogPage(PagerConditions<OperateLog> conditions) {
        return sOperateLogRepository.findAll(conditions);
    }
}
