package com.example.ProductServices.InheritanceTypesExamples.SingleTable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("2")
@Entity
public class Instructor extends User {
    String batch;
    int YOE;
}
