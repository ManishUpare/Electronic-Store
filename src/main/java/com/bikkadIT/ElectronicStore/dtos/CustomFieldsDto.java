package com.bikkadIT.ElectronicStore.dtos;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public class CustomFieldsDto {

    private String isActive;

    private String createdBy;

    private LocalDateTime createdOn;

    private String lastModifiedBy;

    private LocalDateTime modifiedOn;
}
