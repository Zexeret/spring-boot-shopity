package com.ecommerce.shopity.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long productId;
    @NotBlank(message = "Product Name cannot be empty")
    @Size(min = 3, message = "Product Name should be longer than 3 characters")
    private String productName;
    private String description;
    private String image;

    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

    @NotNull(message = "Price cannot be empty")
    private Double price;
    private double discount;

}
