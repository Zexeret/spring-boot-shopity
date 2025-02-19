package com.ecommerce.shopity.payload;

import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long productId;
    private String description;
    private String productName;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;

}
