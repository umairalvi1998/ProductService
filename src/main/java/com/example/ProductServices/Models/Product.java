package com.example.ProductServices.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    @NotBlank
    @Size(min = 3 , message = "Product name must contain atleast 3 characters")
    private String productName;
    private String image;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonBackReference
    private Category category;
    private Double price;
    private Double discount;
    private Double specialPrice;
    @NotBlank
    @Size(min = 6 , message = "Product description must contain atleast 6 characters")
    private String description;
    private Integer quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
