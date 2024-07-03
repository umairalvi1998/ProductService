package com.example.ProductServices.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//Since We will be needing the restTemplate object to create,read products we will not make the object
//of restTemplate everytime. Hence we will use this class which will provide the single object of RestTemplate.
@Configuration //this annotation tells spring that this class is going to have beans.
//Classes annotated with @Configuration are typically used to define Spring beans.
// These beans are managed by the Spring container and can be injected into other components using dependency injection.
public class RestTemplateConfig {
    @Bean //because of this annotation only one object will be created of the RestTemplate
    //@Bean is an annotation used within classes annotated with @Configuration to indicate that a method produces a bean to be managed by the Spring container.
    // This method returns an object that Spring should register as a bean in the application context.
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
