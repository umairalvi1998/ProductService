package com.example.ProductServices.Commons;

import com.example.ProductServices.DTO.UserDto;
import com.example.ProductServices.Exceptions.APIException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationCommons {
    private RestTemplate restTemplate;

    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token) throws APIException {
        //call user Service validateToken API to validate the token.
        ResponseEntity<UserDto> response = null;
        try {
            response = restTemplate.postForEntity("http://localhost:3030/users/validate/"+token,null, UserDto.class);
        } catch (RestClientException e) {
            throw new APIException("Invalid Token");
        }

        if(response.getBody() == null || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            //Invalid Token
            throw new APIException("Invalid Token");
        }
        return response.getBody();
    }
}
