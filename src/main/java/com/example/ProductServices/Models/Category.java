package com.example.ProductServices.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
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
    @OneToMany(/*mappedBy = "category"*/)
    List<Product> products;
    public  Long getId() {
        return id;
    }

}
