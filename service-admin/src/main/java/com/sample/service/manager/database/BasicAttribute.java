package com.sample.service.manager.database;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Getter
@Setter
@MappedSuperclass
public abstract class BasicAttribute implements Serializable {
    
    @Transient
    public static Sort defaultSort =
            Sort.by(Collections.singletonList(Sort.Order.desc("updateTime")));
    
    @Column(columnDefinition = "boolean default false")
    private boolean delFlag;
    
    @Column(length = 32, name = "create_by")
    private String createBy;
    
    @Column(length = 32, name = "update_by")
    private String updateBy;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    private String remark;
    
    public abstract String getId();
    
    public abstract void setId(String id);
    
    @PrePersist
    public void prePersist() {
        if (!StringUtils.hasLength(getId())) {
            setId(UUID.randomUUID().toString());
        }
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = createTime;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updateTime = LocalDateTime.now();
    }
}
