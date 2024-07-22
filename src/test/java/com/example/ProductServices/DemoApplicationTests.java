package com.example.ProductServices;

import com.example.ProductServices.Projections.ProductWithIdAndTitle;
import com.example.ProductServices.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest()
@ContextConfiguration(classes = DemoApplication.class)
class DemoApplicationTests {
      @Autowired
      ProductRepository productRepository;

//     //public DemoApplicationTests(ProductRepository productRepository) {
//         this.productRepository = productRepository;
//     }
    @Test
    void contextLoads() {
    }
   @Test
    void TestDBQueries() {
         List<ProductWithIdAndTitle>  productWithIdAndTitles = productRepository.findIdAndTitle();

         for (ProductWithIdAndTitle productWithIdAndTitle : productWithIdAndTitles) {
             System.out.println(productWithIdAndTitle.getid() + " " + productWithIdAndTitle.gettitle());
         }
    }

}
