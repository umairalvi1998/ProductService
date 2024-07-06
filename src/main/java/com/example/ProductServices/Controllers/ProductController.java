package com.example.ProductServices.Controllers;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
      public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
            /* ResponseEntity<Product> response:

After the constructor executes, response becomes an instance of ResponseEntity<Product>.
This object encapsulates both the Product object returned by productService.getSingleProduct(id) and the HTTP status 200 OK.*/

            ResponseEntity<Product> response=null;

            try {
                  Product p = productService.getSingleProduct(id);
                  response = new ResponseEntity<>(p,HttpStatus.OK);
            }
            catch(RuntimeException e) {
             response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return response;
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
