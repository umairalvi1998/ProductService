package com.example.ProductServices.DTO;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    List<Roles> roles;
    private boolean isEmailVerified;
}