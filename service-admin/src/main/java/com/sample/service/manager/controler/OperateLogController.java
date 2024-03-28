package com.sample.service.manager.controler;

import com.lrenyi.template.service.pojo.web.PageResult;
import com.lrenyi.template.web.function.Log;
import com.lrenyi.template.web.function.log.OperationEnum;
import com.lrenyi.template.web.function.log.OperationObject;
import com.sample.service.manager.database.entity.OperateLog;
import com.sample.service.manager.service.IOperateLogService;
import com.lrenyi.template.service.pojo.web.PagerConditions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
public class OperateLogController {
    
    @Resource
    private IOperateLogService iOperateLogService;
    
    @PostMapping("/query")
    @Log(object = OperationObject.OPERATION_LOG, operation = OperationEnum.QUERY)
    public PageResult<OperateLog> queryLogPage(@RequestBody PagerConditions<OperateLog> conditions) {
        return iOperateLogService.queryLogPage(conditions);
    }
}
