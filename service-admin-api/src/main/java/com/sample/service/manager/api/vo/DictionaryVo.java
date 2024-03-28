package com.sample.service.manager.api.vo;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryVo {
    private String id;
    private String key;
    private String value;
    private boolean status;
    private boolean delFlag;
    private String createBy;
    private String updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
}
