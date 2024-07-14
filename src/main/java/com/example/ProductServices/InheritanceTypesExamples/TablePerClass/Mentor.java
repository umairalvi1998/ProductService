package com.example.ProductServices.InheritanceTypesExamples.TablePerClass;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="TC_Mentor")

public class Mentor extends User {
    private String MentorName;
    private String Company;
    private int YOE;
}
