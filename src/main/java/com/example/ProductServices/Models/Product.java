package com.example.ProductServices.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
  private String title;
  @ManyToOne
  private Category category;
  private Double price;

  public void setId(Long id) {
   this.id = id;
   }

 public Long getId() {
  return id;
  }
}
