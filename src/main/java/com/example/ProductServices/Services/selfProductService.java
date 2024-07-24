package com.example.ProductServices.Services;

import com.example.ProductServices.Exceptions.ProductNotFoundException;
import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Models.Product;
import com.example.ProductServices.Repository.CategoryRepository;
import com.example.ProductServices.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class selfProductService implements ProductService {
     ProductRepository productRepository;
     CategoryRepository categoryRepository;
     public selfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
         this.productRepository = productRepository;
         this.categoryRepository = categoryRepository;
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
    public List<Product> getAllProducts() {
         return  productRepository.findAll();

    }

    @Override
    public Product updateProduct(Long id, Product product) {
       Optional<Product> prod = productRepository.findById(id);
       if(prod.isEmpty()){
           throw new ProductNotFoundException("Product not found");
       }
      Product productInDb = prod.get();

       if(product.getTitle() != null) {
           productInDb.setTitle(product.getTitle());
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

        if(product.getTitle() != null) {
            productInDb.setTitle(product.getTitle());
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

        }
        return productRepository.save(productInDb);

    }

    @Override
    public Product deleteProduct(Long id) {
        productRepository.deleteById(id);
        return  null;
    }

    @Override
    public Product addProduct(Product product) {
         Category category = product.getCategory();

//         if(category.getId()==null) {
//             //we need to create a new category object in the DB
//             category = categoryRepository.save(category);
//             product.setCategory(category);
//         }

        return productRepository.save(product);
    }
}
