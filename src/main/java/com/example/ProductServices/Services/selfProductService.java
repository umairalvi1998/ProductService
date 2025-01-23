package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.ProductDto;
import com.example.ProductServices.Exceptions.ProductNotFoundException;
import com.example.ProductServices.Exceptions.ResourceNotFoundException;
import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Repository.CategoryRepository;
import com.example.ProductServices.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service("selfProductService")
public class selfProductService implements ProductService {
     private ProductRepository productRepository;
     private CategoryRepository categoryRepository;
     private ModelMapper modelMapper;
     private FileService fileService;
     @Value("${project.image}")
     private String path;

     public selfProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, FileService fileService) {
         this.productRepository = productRepository;
         this.categoryRepository = categoryRepository;
         this.modelMapper = modelMapper;
         this.fileService = fileService;
     }
    @Override
    public Product getSingleProduct(long productId) {
        Optional<Product> prod = productRepository.findById(productId);
        if(prod.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }

        return prod.get();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
         return productRepository.findAll(PageRequest.of(pageNumber,
                 pageSize,
                 Sort.by("price").ascending()));

    }

    @Override
    public Product updateProduct(Long id, Product product) {
       Optional<Product> prod = productRepository.findById(id);
       if(prod.isEmpty()){
           throw new ProductNotFoundException("Product not found");
       }
      Product productInDb = prod.get();

       if(product.getProductName() != null) {
           productInDb.setProductName(product.getProductName());
       }
       if(product.getPrice() != null )
       {
           productInDb.setPrice(product.getPrice());
       }
       if(product.getDiscount() != null) {
           productInDb.setDiscount(product.getDiscount());
           productInDb.setSpecialPrice(product.getPrice() - (product.getDiscount() * 0.01)*product.getPrice());
       }
       if(product.getDescription() != null) {
           productInDb.setDescription(product.getDescription());
       }
       if(product.getQuantity() != null) {
            productInDb.setQuantity(product.getQuantity());
       }
       if(product.getCategory() != null) {
           Optional<Category> cat = categoryRepository.findById(product.getCategory().getId());
           if(cat.isEmpty())
           {
               Category newCategory = product.getCategory();
               categoryRepository.save(newCategory);
               productInDb.setCategory(newCategory);
           }
           else {
               productInDb.setCategory(cat.get());
           }

       }
       return productRepository.save(productInDb);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        //this is  a PUT call.
        Optional<Product> prod = productRepository.findById(id);
        if(prod.isEmpty()){
            throw new ProductNotFoundException("Product not found, Cant't replace the product which does not exists");
        }

        Product productInDb = prod.get();

        if(product.getProductName() != null) {
            productInDb.setProductName(product.getProductName());
        }
        if(product.getPrice() != null )
        {
            productInDb.setPrice(product.getPrice());
        }
        if(product.getCategory() != null) {
            Optional<Category> cat = categoryRepository.findById(product.getCategory().getId());
            if(cat.isEmpty())
            {
                Category newCategory = product.getCategory();
                categoryRepository.save(newCategory);
                productInDb.setCategory(newCategory);
            }
            else {
                productInDb.setCategory(cat.get());
            }

        }
        return productRepository.save(productInDb);

    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        int rowsAffected = productRepository.deleteProductById(id);

        if (rowsAffected == 0) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product addProduct(@Valid  Product product, long categoryId) throws ResourceNotFoundException {
//         Category category = product.getCategory();

//         if(category.getId()==null) {
//             //we need to create a new category object in the DB
//             category = categoryRepository.save(category);
//             product.setCategory(category);
//         }

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        product.setCategory(category);
        product.setImage("default.jpg");
        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01)*product.getPrice();
        product.setSpecialPrice(specialPrice);

        return productRepository.save(product);
    }

    @Override
    public Page<Product> searchByCategory(long categoryId,int pageNumber,int pageSize) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        return productRepository.findByCategory(category,PageRequest.of(pageNumber,pageSize,Sort.by("price")));

    }

    @Override
    public Page<Product> searchProductByKeyword(String keyword, int pageNumber, int pageSize) {
         Page<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%',PageRequest.of(pageNumber,pageSize,Sort.by("price")));
         if(products.isEmpty()) {
             throw new ProductNotFoundException("Products with the provided keyword does not exist");
         }
         return products;
    }

    @Override
    public ProductDto updateProductImage(long productId, MultipartFile image) throws IOException {
         //Get the product from Db
        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        //upload the image to the server
        //get the filename of the uploaded image

        String fileName = fileService.uploadImage(path,image);
        //updating the new filename to the product
        productFromDb.setImage(fileName);

        //save updated Product
        Product updatedProduct = productRepository.save(productFromDb);

        //return the productDto
       return modelMapper.map(updatedProduct,ProductDto.class);


    }


}
