package com.example.ProductServices.Models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel {
 private String title;
 private Category category;
 private double price;

}
