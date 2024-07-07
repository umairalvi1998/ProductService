package com.example.ProductServices.Exceptions;
/* this package of EXception is made to store custom exceptions*/
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
         super(message);
    }
}
