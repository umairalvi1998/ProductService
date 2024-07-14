package com.example.ProductServices.InheritanceTypesExamples.TablePerClass;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="TC_Instructor")

public class Instructor extends User {
    private String module;
}
