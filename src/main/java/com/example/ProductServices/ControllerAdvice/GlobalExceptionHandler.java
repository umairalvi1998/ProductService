package com.example.ProductServices.ControllerAdvice;

import com.example.ProductServices.DTO.ExceptionDTO;
import com.example.ProductServices.Exceptions.APIException;
import com.example.ProductServices.Exceptions.ProductNotFoundException;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String, String>>(response,
                HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
//        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException e) {
        String message = e.getMessage();
//        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
