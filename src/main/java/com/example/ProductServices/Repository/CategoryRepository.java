package com.example.ProductServices.Repository;

import com.example.ProductServices.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);
    Category findById(long id);
}
