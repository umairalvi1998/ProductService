package com.example.ProductServices.Controllers;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

      private ProductService productService;

      public ProductController(ProductService productService) {
            this.productService = productService;
      }

      @GetMapping("/{id}")
      public Product getProductById(@PathVariable("id") long id) {
            return productService.getSingleProduct(id);
      }

      public List<Product> getAllProducts() {
            return new ArrayList<Product>();
      }
      

}
