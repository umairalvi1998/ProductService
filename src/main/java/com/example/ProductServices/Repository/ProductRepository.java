package com.example.ProductServices.Repository;

import com.example.ProductServices.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Product Repo should contain all the methods (CRUD) related to product model.
    Optional<Product> findById(Long id);
    List<Product> findAll();

    @Override
    void deleteById(Long aLong);

    Product save(Product product);

}
