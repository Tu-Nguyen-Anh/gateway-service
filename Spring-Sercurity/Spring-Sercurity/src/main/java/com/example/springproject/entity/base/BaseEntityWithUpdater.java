package com.example.springproject.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Extension of BaseEntity that includes fields for tracking the last updater and the timestamp of the last update.
 * This class is intended to be used as a base for entities that require tracking of modification details.
 */
@Data
@MappedSuperclass
public class BaseEntityWithUpdater extends BaseEntity {

  @LastModifiedBy
  private String lastUpdatedBy;

  @LastModifiedDate
  private Long lastUpdatedAt;

}

