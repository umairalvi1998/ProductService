package com.example.ProductServices.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseModel implements Serializable {
 @Id
 @GeneratedValue(strategy = IDENTITY)
 protected Long id;
 @CreatedDate
 @Temporal(value = TemporalType.TIMESTAMP)
 private Date createdAt;
 @LastModifiedDate
 @Temporal(value = TemporalType.TIMESTAMP)
 private Date updatedAt;
}
