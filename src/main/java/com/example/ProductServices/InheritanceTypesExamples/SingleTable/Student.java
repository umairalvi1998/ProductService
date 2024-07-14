package com.example.ProductServices.InheritanceTypesExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("0")
@Entity

public class Student extends User {
    String batch;
}
