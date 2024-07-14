package com.example.ProductServices.InheritanceTypesExamples.JoinedTable;

import jakarta.persistence.*;
import  lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="JT_User")
public class User {
    @Id
    private int id;
    private String name;
    private String email;

}
