package com.example.ProductServices.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
String name;
String description;


}
