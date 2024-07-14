package com.example.ProductServices.InheritanceTypesExamples.JoinedTable;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import  lombok.Setter;

@Getter
@Setter
@Entity(name="JT_Mentor")
@PrimaryKeyJoinColumn(name="user_Id")

public class Mentor extends User{
    private String MentorName;
    private String Company;
    private int YOE;
}
