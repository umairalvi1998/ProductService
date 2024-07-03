package com.example.ProductServices.Services;

import com.example.ProductServices.Models.Product;

import java.util.List;
/* for now we are using fakeStore product service, but In future we might be using our own database to
fetch products. Hence we have made an interface so that different classes can implement this interface
 */
public interface ProductService {
    Product getSingleProduct(long productId);
    List<Product> getAllProducts();
}
