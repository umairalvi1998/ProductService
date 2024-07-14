package com.example.ProductServices.InheritanceTypesExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("1")
@Entity
public class Mentor extends User {
    String CompanyName;
    int YOE;
    String Specialization;
}
