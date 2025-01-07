package com.ecommerce.shopity.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryID;

    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 3, message = "Category Name should be longer than 3 characters")
    private String categoryName;
}
