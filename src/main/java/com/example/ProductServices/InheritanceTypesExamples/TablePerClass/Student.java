package com.example.ProductServices.InheritanceTypesExamples.TablePerClass;


import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="TC_Student")

public class Student extends User {
    String batch;
}
