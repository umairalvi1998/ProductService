package com.example.ProductServices.Services;

import com.example.ProductServices.DTO.fakeStoreProductDTO;
import com.example.ProductServices.Exceptions.ProductNotFoundException;
import com.example.ProductServices.Models.Category;
import com.example.ProductServices.Models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service("fakeStoreProductService")
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
        if(DTOobj == null) {
            throw new ProductNotFoundException("Product not found with ID "+productId);
        }
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
         products.add(convertFakeStoreProductDto(FakeStoreProductDTO));
        }
        return products;
   }

   @Override
    public Product updateProduct(Long id, Product product) {
       //PUT
       /*
       Return a RequestCallback implementation that:
        Sets the request Accept header based on the given response type, cross-checked against the configured message converters.
        Writes the given object to the request stream.
       requestBody: This parameter is of type Object and is nullable (@Nullable). It likely represents the data that will be sent as part of the HTTP request body.
       responseType: This parameter is of type Type and probably specifies the expected type of the response that the client expects to receive.

        */
       fakeStoreProductDTO prod = new fakeStoreProductDTO();
       prod.setId(product.getId());
       prod.setTitle(product.getTitle());
       prod.setPrice(product.getPrice());
       prod.setCategory(product.getCategory().getName());
       prod.setDescription(product.getCategory().getDescription());

       RequestCallback requestCallback =restTemplate.httpEntityCallback(prod,fakeStoreProductDTO.class);
       HttpComponentsClientHttpRequestFactory requestFactory =  new HttpComponentsClientHttpRequestFactory();
       restTemplate.setRequestFactory(requestFactory);
       HttpMessageConverterExtractor<fakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor(fakeStoreProductDTO.class, restTemplate.getMessageConverters());
       /*@Nullable
       public <T> T execute(URI url,
               HttpMethod method,
               @Nullable
                       RequestCallback requestCallback,
               @Nullable
                       ResponseExtractor<T> responseExtractor)
              throws RestClientException
       Execute the HTTP method to the given URL, preparing the request with the RequestCallback, and reading the response with a ResponseExtractor.
               Parameters:
        url - the URL
        method - the HTTP method (GET, POST, etc)
        requestCallback - object that prepares the request
        responseExtractor - object that extracts the return value from the response

        Returns: an arbitrary object, as returned by the ResponseExtractor
        */
       fakeStoreProductDTO response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PATCH, requestCallback, responseExtractor);
       return convertFakeStoreProductDto(response);
   }
    @Override
    public Product replaceProduct(Long id,Product product) {
       /* When we are passing the product as request body the issue which is arising is that, fakeStore.com is not able to
       convert our product object into their product object. Specifically it is not able deserialize the Category object
       to String.
       That is why we need to convert our product object to fakeStoreDTO.
        */
        fakeStoreProductDTO prod = new fakeStoreProductDTO();
        prod.setId(product.getId());
        prod.setTitle(product.getTitle());
        prod.setPrice(product.getPrice());
        prod.setCategory(product.getCategory().getName());
        prod.setDescription(product.getCategory().getDescription());
        RequestCallback requestCallback =restTemplate.httpEntityCallback(prod,fakeStoreProductDTO.class);
        HttpMessageConverterExtractor<fakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor(fakeStoreProductDTO.class, restTemplate.getMessageConverters());
        fakeStoreProductDTO response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null) {
            throw new ProductNotFoundException("Product not found with ID "+id);
        }
        return convertFakeStoreProductDto(response);
    }
     @Override
    public Product deleteProduct(Long id) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(null,fakeStoreProductDTO.class);
        HttpMessageConverterExtractor<fakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor(fakeStoreProductDTO.class, restTemplate.getMessageConverters());
        fakeStoreProductDTO response =  restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.DELETE, requestCallback, responseExtractor);
        if(response == null) {
            throw new ProductNotFoundException("Product not found with ID "+id);
        }
        return convertFakeStoreProductDto(response);
   }

    @Override
    public Product addProduct(Product product) {
        return null;
    }


}
