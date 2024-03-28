package com.sample.service.manager.database.entity;

import com.sample.service.manager.database.BasicAttribute;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sys_user")
public class SysUser extends BasicAttribute {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    private String userName;
    
    private String jobNumber;
    
    @Column(length = 512)
    private String password;
    
    @Column(columnDefinition = "boolean default false")
    private boolean disabled;
    
    @Column(columnDefinition = "boolean default false")
    private boolean expired;
    
    @Column(columnDefinition = "boolean default false")
    private boolean locked;
}
