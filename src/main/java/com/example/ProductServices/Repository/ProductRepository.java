package com.example.ProductServices.Repository;

import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Projections.ProductWithIdAndproductName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Product Repo should contain all the methods (CRUD) related to product model.
    Optional<Product> findById(Long id);
    
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

//    @Override
//    void deleteById(Long productId);
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    int deleteProductById(@Param("id") Long id);


    Product save(Product product);

    @Query("SELECT p.id as id,p.productName as productName FROM Product p")
    List<ProductWithIdAndproductName>  findIdAndproductName();

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);
}
