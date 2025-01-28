package com.example.ProductServices.Controllers;

import com.example.ProductServices.DTO.ProductResponse;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    @Qualifier("selfProductService")
    private ProductService productService;

    @Test
    void TestGetProductById() {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productService.getSingleProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getId());
    }

    @Test
    void getAllProducts_ReturnsProductResponse_WhenProductsExist() {
        int pageNumber = 0;
        int pageSize = 10;
        Page<Product> productPage = mock(Page.class);
        when(productService.getAllProducts(pageNumber, pageSize)).thenReturn(productPage);
        when(productPage.getContent()).thenReturn(new ArrayList<>());
        when(productPage.getNumber()).thenReturn(pageNumber);
        when(productPage.getSize()).thenReturn(pageSize);
        when(productPage.isLast()).thenReturn(true);
        when(productPage.getTotalPages()).thenReturn(1);
        when(productPage.getTotalElements()).thenReturn(10L);

        ResponseEntity<ProductResponse> response = productController.getAllProducts(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(pageNumber, response.getBody().getPageNumber());
        assertEquals(pageSize, response.getBody().getPageSize());
        assertTrue(response.getBody().isLastPage());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(10L, response.getBody().getTotalElements());
    }
}