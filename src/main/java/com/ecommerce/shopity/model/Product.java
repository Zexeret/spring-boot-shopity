package com.ecommerce.shopity.model;

import com.ecommerce.shopity.payload.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product Name cannot be empty")
    @Size(min = 3, message = "Product Name should be longer than 3 characters")
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user ;


    public void updateProductFromDTO(ProductDTO productDTO) {
        this.setProductName(productDTO.getProductName());
        this.setDescription(productDTO.getDescription());
        this.setImage(productDTO.getImage());
        this.setQuantity(productDTO.getQuantity());
        this.setPrice(productDTO.getPrice());
        this.setDiscount(productDTO.getDiscount());
        this.setSpecialPrice(productDTO.getPrice() - (productDTO.getDiscount() / 100) * productDTO.getPrice());
    }
}
