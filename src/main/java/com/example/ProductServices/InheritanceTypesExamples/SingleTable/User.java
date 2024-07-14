package com.example.ProductServices.InheritanceTypesExamples.SingleTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="st_User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (
    name = "user_type",
    discriminatorType = DiscriminatorType.INTEGER
)
public class User {
    @Id
    private int id;
    private String name;
    private String email;
}
