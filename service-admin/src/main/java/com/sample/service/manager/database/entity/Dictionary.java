package com.sample.service.manager.database.entity;

import com.sample.service.manager.database.BasicAttribute;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Dictionary extends BasicAttribute {
    public static final String SESSION_OUT_TIME_KEY = "session_out_time";
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    
    private String key;
    private String value;
    private boolean status;
}
