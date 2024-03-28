package com.sample.service.manager.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lrenyi.template.core.util.BeanUtils;
import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.web.function.log.OperateLogVo;
import com.lrenyi.template.web.function.log.service.IOperateLogAspectService;
import com.sample.service.manager.database.entity.OperateLog;
import com.sample.service.manager.database.repository.IOperateLogRepository;
import com.sample.service.manager.service.IOperateLogService;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class OperateLogAspectService implements IOperateLogAspectService, IOperateLogService {
    
    @Resource
    private IOperateLogRepository iOperateLogRepository;
    
    @Override
    public void logHandle(OperateLogVo logVo) {
        OperateLog operateLog = new OperateLog();
        BeanUtils.copyProperties(logVo, operateLog);
        OperateLog saved = iOperateLogRepository.saveOrUpdate(operateLog);
        if (!StringUtils.hasLength(saved.getId())) {
            log.warn("保存操作日志失败: {}", JSON.toJSONString(operateLog));
        }
    }
    
    @Override
    public PageResult<OperateLog> queryLogPage(PagerConditions<OperateLog> conditions) {
        Page<OperateLog> operateLogs = iOperateLogRepository.queryLogPage(conditions);
        return PageResult.getSuccess(operateLogs);
    }
}
