package com.example.ProductServices.ControllerAdvice;

import com.example.ProductServices.DTO.ExceptionDTO;
import com.example.ProductServices.Exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(ArithmeticException ex) {
        ResponseEntity<String> response = new ResponseEntity<>("Something Went Wrong,An arithmetic Exception Occured", HttpStatus.FORBIDDEN);
        return response;
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> ProductNotFoundException(NullPointerException ex) {
        ResponseEntity<String> response = new ResponseEntity<>("Something Went Wrong,Product Not Found", HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDTO> ProductNotFoundException(ProductNotFoundException ex) {
       ExceptionDTO exceptionDTO = new ExceptionDTO();
       exceptionDTO.setMessage(ex.getMessage());
       exceptionDTO.setSolution("Enter correct Product ID");
       exceptionDTO.setTimestamp(LocalDateTime.now());
       exceptionDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

       return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }


}
