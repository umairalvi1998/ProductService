package com.example.ProductServices.Controllers;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.springframework.web.bind.annotation.*;

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

      @GetMapping() //we want the ApI to be like /products hence no parameters
      public List<Product> getAllProducts() {
            return productService.getAllProducts();
      }
      
      public void delteteProduct() {

      }
      @PutMapping("/{id}")
      public Product replaceProduct(@PathVariable("id") Long id,@RequestBody Product product) {
            return null;

      }

      @PatchMapping("/{id}")
      public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
            return productService.updateProduct(id,product);
      }

}
