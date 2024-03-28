package com.sample.service.manager.database.entity;

import com.sample.service.manager.database.BasicAttribute;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class OperateLog extends BasicAttribute {
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    private String operator;
    private LocalDateTime time;
    private String location;
    private String object;
    private String operation;
    private boolean resultStatus;
    private String requestParameters;
    private String returnResults;
    private String errorMessage;
}
