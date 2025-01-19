package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.CategoryRequestDto;
import com.example.ProductServices.DTO.CategoryResponseDto;
import com.example.ProductServices.Exceptions.APIException;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponseDto getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) throws APIException {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty())
            throw new APIException("No category created till now.");

        List<CategoryRequestDto> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryRequestDto.class))
                .toList();

        CategoryResponseDto categoryResponse = new CategoryResponseDto();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryRequestDto createCategory(CategoryRequestDto categoryDTO) throws APIException {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByName(category.getName());
        if (categoryFromDb != null)
            throw new APIException("Category with the name " + category.getName() + " already exists !!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryRequestDto.class);
    }

    @Override
    public CategoryRequestDto deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryRequestDto.class);
    }

    @Override
    public CategoryRequestDto updateCategory(CategoryRequestDto categoryDTO, Long categoryId) throws ResourceNotFoundException {
         categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setId(categoryId);
       Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryRequestDto.class);
    }
}