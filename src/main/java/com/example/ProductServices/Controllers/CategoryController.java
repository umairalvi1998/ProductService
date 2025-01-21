package com.example.ProductServices.Controllers;

import com.example.ProductServices.Configurations.AppConstants;
import com.example.ProductServices.DTO.CategoryRequestDto;
import com.example.ProductServices.DTO.CategoryResponseDto;
import com.example.ProductServices.Exceptions.APIException;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import com.example.ProductServices.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponseDto> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) throws APIException {
        CategoryResponseDto categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryRequestDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryDTO) throws APIException {
        CategoryRequestDto savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDto> deleteCategory(@PathVariable Long categoryId) throws ResourceNotFoundException {
        CategoryRequestDto deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }


    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDto> updateCategory(@Valid @RequestBody CategoryRequestDto categoryDTO,
                                                             @PathVariable Long categoryId) throws ResourceNotFoundException {
        CategoryRequestDto savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

}