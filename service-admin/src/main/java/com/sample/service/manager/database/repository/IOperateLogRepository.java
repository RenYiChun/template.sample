package com.sample.service.manager.database.repository;

import com.sample.service.manager.database.entity.OperateLog;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import org.springframework.data.domain.Page;

public interface IOperateLogRepository {
    
    OperateLog saveOrUpdate(OperateLog log);
    
    Page<OperateLog> queryLogPage(PagerConditions<OperateLog> conditions);
}
