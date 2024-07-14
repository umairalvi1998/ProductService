package com.example.ProductServices.InheritanceTypesExamples.TablePerClass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="TC_User")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    private int id;
    private String name;
    private String email;

}
