package com.example.ProductServices.Repository;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Projections.ProductWithIdAndTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Product Repo should contain all the methods (CRUD) related to product model.
    Optional<Product> findById(Long id);
    
    Page<Product> findAll(Pageable pageable);

    @Override
    void deleteById(Long aLong);

    Product save(Product product);

    @Query("SELECT p.id as id,p.title as title FROM Product p")
    List<ProductWithIdAndTitle>  findIdAndTitle();

}
