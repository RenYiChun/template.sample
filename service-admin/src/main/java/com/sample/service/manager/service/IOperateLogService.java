package com.sample.service.manager.service;

import com.lrenyi.template.service.pojo.web.PageResult;
import com.sample.service.manager.database.entity.OperateLog;
import com.lrenyi.template.service.pojo.web.PagerConditions;

public interface IOperateLogService {
    
    PageResult<OperateLog> queryLogPage(PagerConditions<OperateLog> conditions);
}
