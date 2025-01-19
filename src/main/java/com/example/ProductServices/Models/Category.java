package com.example.ProductServices.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    @NotBlank
    @Size(min = 5, message = "Category name must contain atleast 5 characters")
    String name;
    String description;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Product> products;

    public  Long getId() {
        return id;
    }

}
