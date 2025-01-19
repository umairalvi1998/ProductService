package com.example.ProductServices.Exceptions;

public class APIException extends Exception {

    //the purpose to define this exception class is to handle API related exceptions like when creating a category
    //if a category already exists it should throw an exception that category already exists,  exceptions like this
    //will be represented by this class, because these kind of exceptions are different from standard exceptions.
    private static final long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}