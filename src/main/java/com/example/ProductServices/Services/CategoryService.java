package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.CategoryRequestDto;
import com.example.ProductServices.DTO.CategoryResponseDto;
import com.example.ProductServices.Exceptions.APIException;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;

public interface CategoryService {
    CategoryResponseDto getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) throws APIException;
    CategoryRequestDto createCategory(CategoryRequestDto categoryDTO) throws APIException;

    CategoryRequestDto deleteCategory(Long categoryId) throws ResourceNotFoundException;

    CategoryRequestDto updateCategory(CategoryRequestDto categoryDTO, Long categoryId) throws ResourceNotFoundException;
}
