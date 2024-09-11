package com.example.ProductServices.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class BaseModel implements Serializable {
 @Id
 @GeneratedValue(strategy = IDENTITY)
 protected  Long id;
 protected  Date createdAt;
 protected Date updatedAt;
}
