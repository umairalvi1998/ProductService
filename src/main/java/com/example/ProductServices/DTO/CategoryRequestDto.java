package com.example.ProductServices.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDto {
    private String categoryName;
    private String description;
}
