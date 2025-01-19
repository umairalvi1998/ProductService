package com.example.ProductServices.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDto {
    private Long categoryId;
    private String categoryName;
}
