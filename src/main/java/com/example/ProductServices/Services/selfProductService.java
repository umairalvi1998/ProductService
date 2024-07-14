package com.example.ProductServices.Services;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class selfProductService implements ProductService {
     ProductRepository productRepository;
     public selfProductService(ProductRepository productRepository) {
         this.productRepository = productRepository;
     }
    @Override
    public Product getSingleProduct(long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
