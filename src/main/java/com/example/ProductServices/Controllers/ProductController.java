package com.example.ProductServices.Controllers;

import com.example.ProductServices.DTO.ProductDto;
import com.example.ProductServices.DTO.ProductResponse;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

      private ProductService productService;

      private ModelMapper modelMapper;

      public ProductController(@Qualifier("selfProductService") ProductService productService, ModelMapper modelMapper) {
            this.productService = productService;
            this.modelMapper = modelMapper;
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
      public ResponseEntity<ProductResponse> getAllProducts(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
            Page<Product> productPages = productService.getAllProducts(pageNumber,pageSize);
              List<ProductDto> productDtos = new ArrayList<>();

              productPages.stream().map(product -> modelMapper.map(product, ProductDto.class)).forEach(productDtos::add);

              ProductResponse response = new ProductResponse();

              response.setContent(productDtos);
              response.setPageNumber(productPages.getNumber());
              response.setPageSize(productPages.getSize());
              response.setLastPage(productPages.isLast());
              response.setTotalPages(productPages.getTotalPages());
              response.setTotalElements(productPages.getTotalElements());

              return new ResponseEntity<>(response, HttpStatus.OK);
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

      @PostMapping("/categories/{categoryId}/product")
      public ResponseEntity<ProductDto>  addProduct(@RequestBody Product product, @PathVariable("categoryId") long categoryId) throws ResourceNotFoundException {

            Product prod = productService.addProduct(product,categoryId);
            ProductDto productDto = modelMapper.map(prod,ProductDto.class);

            return new ResponseEntity<>(productDto,HttpStatus.CREATED);
      }

      @GetMapping("/categories/{categoryId}")
      public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable("categoryId") long categoryId,@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws ResourceNotFoundException {
          Page<Product> productPages = productService.searchByCategory(categoryId,pageNumber,pageSize);

          List<ProductDto> productDtos = new ArrayList<>();

          productPages.stream().map(product -> modelMapper.map(product, ProductDto.class)).forEach(productDtos::add);

          ProductResponse response = new ProductResponse();

          response.setContent(productDtos);
          response.setPageNumber(productPages.getNumber());
          response.setPageSize(productPages.getSize());
          response.setLastPage(productPages.isLast());
          response.setTotalPages(productPages.getTotalPages());
          response.setTotalElements(productPages.getTotalElements());

          return new ResponseEntity<>(response, HttpStatus.OK);
      }
}
