package com.example.ProductServices.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class BaseModel {
 @Id
 @GeneratedValue(strategy = IDENTITY)
 protected  Long id;
 protected  Date createdAt;
 protected Date updatedAt;
}
