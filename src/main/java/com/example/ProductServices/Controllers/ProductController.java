package com.example.ProductServices.Controllers;

import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
      @Autowired
      @Qualifier("selfProductService")
      private ProductService productService;

      public ProductController(@Qualifier("selfProductService") ProductService productService) {
            this.productService = productService;
      }

      @GetMapping("/{id}")
      public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
            /* ResponseEntity<Product> response:

After the constructor executes, response becomes an instance of ResponseEntity<Product>.
This object encapsulates both the Product object returned by productService.getSingleProduct(id) and the HTTP status 200 OK.*/

//            ResponseEntity<Product> response=null;
//
//            try {
//                  Product p = productService.getSingleProduct(id);
//                  response = new ResponseEntity<>(p,HttpStatus.OK);
//            }
//            catch(RuntimeException e) {
//             response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            return response;

          ResponseEntity<Product> response = new ResponseEntity<>(productService.getSingleProduct(id),HttpStatus.OK);
          return response;
      }

      @GetMapping() //we want the ApI to be like /products hence no parameters
      public List<Product> getAllProducts() {
            return productService.getAllProducts();
      }


      @DeleteMapping("/{id}")
      public Product  delteteProduct(@PathVariable("id") Long id) {
             return  productService.deleteProduct(id);
      }
      @PutMapping("/{id}")
      public Product replaceProduct(@PathVariable("id") Long id,@RequestBody Product product) {
            return  productService.replaceProduct(id,product);

      }

      @PatchMapping("/{id}")
      public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
            return productService.updateProduct(id,product);
      }

      @PostMapping()
      public Product addProduct(@RequestBody Product product) {
            return productService.addProduct(product);
      }
}
