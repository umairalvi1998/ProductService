package com.example.ProductServices.InheritanceTypesExamples.JoinedTable;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import  lombok.Setter;

@Getter
@Setter
@Entity(name="JT_Instructor")
@PrimaryKeyJoinColumn(name="user_Id")

public class Instructor extends User  {
    private String module;
}
