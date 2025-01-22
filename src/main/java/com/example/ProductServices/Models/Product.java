package com.example.ProductServices.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String productName;
    private String image;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonBackReference
    private Category category;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private String description;
    private Integer quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
