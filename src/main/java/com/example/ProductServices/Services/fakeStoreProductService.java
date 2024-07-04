package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.fakeStoreProductDTO;
import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class fakeStoreProductService implements ProductService {
   private RestTemplate restTemplate;

   fakeStoreProductService(RestTemplate restTemplate) {

       this.restTemplate = restTemplate;
   }

    public Product getSingleProduct(long productId) {
       //call fakestore to fetch the product with the given id. => make HTTP call
       fakeStoreProductDTO DTOobj = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, fakeStoreProductDTO.class);
       /*We need to convert our fakeStoreProductDTO into the format of our Product class.
       * Because we are fetching the product details from outside and those details might not be
       * compatible with my Product details. Hence we created a DTO . Now we need to convert the
       * DTO to our product.  */
        return  convertFakeStoreProductDto(DTOobj);

    }

    public Product convertFakeStoreProductDto(fakeStoreProductDTO DTOobj) {
       //converted this to separate method because we will be needing this multiple times,hence
       //to avoid code duplication.
       Product product = new Product();

        product.setTitle(DTOobj.getTitle());
        product.setPrice(DTOobj.getPrice());
        product.setId(DTOobj.getId());

        Category category = new Category();
        category.setName(DTOobj.getCategory());
        category.setDescription(DTOobj.getDescription());

        product.setCategory(category);

        return product;

    }
    public List<Product> getAllProducts() {
       fakeStoreProductDTO[]  fakeStoreProductDTOS= restTemplate.getForObject("https://fakestoreapi.com/products",fakeStoreProductDTO[].class);
       /*  The reason why restTemplate.getForObject("https://fakestoreapi.com/products", fakeStoreProductDTO[].class) does not give you an error lies in how Java handles arrays compared to generic types (like List<fakeStoreProductDTO>).

Explanation:
Array Type Information:

In Java, arrays retain their type information at runtime (fakeStoreProductDTO[].class).
When you use fakeStoreProductDTO[].class, Java understands that you are referring to an array of fakeStoreProductDTO objects.
Therefore, RestTemplate can correctly deserialize the JSON response from "https://fakestoreapi.com/products" into an array of fakeStoreProductDTO objects.
No Type Erasure Issue:

Unlike parameterized types (e.g., List<fakeStoreProductDTO>), where type erasure occurs and the type information (List<fakeStoreProductDTO>.class) is not available at runtime Instead, it's compiled into List.class, which does not match what Spring's RestTemplate needs to correctly deserialize JSON into a List<fakeStoreProductDTO>.
, arrays (fakeStoreProductDTO[].class) do retain their type information.
This means that RestTemplate can directly use fakeStoreProductDTO[].class to deserialize the JSON response without needing additional type information that would be lost due to type erasure.
Usage in getForObject:

The method restTemplate.getForObject(url, responseType) is typically used when you expect a single object or a primitive type as the response.
In your case, by specifying fakeStoreProductDTO[].class, you're indicating to RestTemplate that the response from "https://fakestoreapi.com/products" should be deserialized into an array of fakeStoreProductDTO objects.*/
       //Convert List of FakeStoreProductDTO into the list of product.

        List<Product> products = new ArrayList<>();
        for(fakeStoreProductDTO FakeStoreProductDTO : fakeStoreProductDTOS) {
         products.add(convertFakeStoreProductDto(FakeStoreProductDTO));;
        }
        return products;
   }

   @Override
    public Product updateProduct(Long id, Product product) {
       //PUT
       RequestCallback requestCallback = restTemplate.httpEntityCallback(product, fakeStoreProductDTO.class);
       HttpMessageConverterExtractor<fakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor(fakeStoreProductDTO.class, restTemplate.getMessageConverters());
       fakeStoreProductDTO response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PATCH, requestCallback, responseExtractor);
       return convertFakeStoreProductDto(response);
   }


}
