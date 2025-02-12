package com.example.ProductServices.Controllers;

import com.example.ProductServices.DTO.ProductDto;
import com.example.ProductServices.DTO.ProductResponse;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    @Qualifier("selfProductService")
    private ProductService productService;

    @Mock
    private ModelMapper modelMapper;

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

    @Test
    void TestAddProduct_ShouldReturnCreatedProduct() throws ResourceNotFoundException {
            //1) Arrange
        long categoryId = 1L;
        Product inputProduct = new Product();
        Product savedProduct = new Product();
        savedProduct.setId(12L); //Assuming service sets the product Id after saving the product.
        savedProduct.setProductName("Water Bottle");

        ProductDto expectedDto = new ProductDto();
        expectedDto.setProductId(12L);
        expectedDto.setProductName("Water Bottle");


        //Mock Service and Model Mapper
        when(productService.addProduct(inputProduct,categoryId)).thenReturn(savedProduct);
        when(modelMapper.map(savedProduct, ProductDto.class)).thenReturn(expectedDto);

        // 2) ACT
        ResponseEntity<ProductDto> response =
                productController.addProduct(inputProduct, categoryId);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());

        // Verify interactions
        verify(productService).addProduct(inputProduct, categoryId);
        verify(modelMapper).map(savedProduct, ProductDto.class);

    }

    @Test
    void addProduct_WhenCategoryDoesNotExist_ThrowsException() throws ResourceNotFoundException {
        // Arrange
        long invalidCategoryId = 999L;
        Product inputProduct = new Product();

        when(productService.addProduct(inputProduct, invalidCategoryId))
                .thenThrow(new ResourceNotFoundException("Category","Id","CategoryId"));

        // Act & Assert
        try {
            productController.addProduct(inputProduct, invalidCategoryId);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Category not found", ex.getMessage());
        }
    }
}