package com.example.ProductServices.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    String name;
    String description;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Product> products;
    public  Long getId() {
        return id;
    }

}
