package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.fakeStoreProductDTO;
import com.example.ProductServices.Models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class fakeStoreProductService implements ProductService {
   private RestTemplate restTemplate;

   fakeStoreProductService(RestTemplate restTemplate) {

       this.restTemplate = restTemplate;
   }

    public Product getSingleProduct(long productId) {
       //call fakestore to fetch the product with the given id. => make HTTP call
        restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, fakeStoreProductDTO.class);

    }
    public List<Product> getAllProducts() {
        return new ArrayList<Product>();
    }

}
