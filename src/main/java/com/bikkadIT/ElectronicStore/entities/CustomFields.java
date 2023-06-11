package com.bikkadIT.ElectronicStore.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomFields {

    @Column(name = "is_active_switch",updatable = true)
    private String isActive;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "update_date",updatable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

}