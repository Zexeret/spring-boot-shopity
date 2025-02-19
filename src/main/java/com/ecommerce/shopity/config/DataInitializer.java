package com.ecommerce.shopity.config;


import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.model.Product;
import com.ecommerce.shopity.payload.ProductDTO;
import com.ecommerce.shopity.repositories.CategoryRepository;

import com.ecommerce.shopity.repositories.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;
private final ProductRepository productRepository;
private final ObjectMapper objectMapper;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            //Initialize Category
            Category categoryOne = Category.builder().categoryName("Food").build();
            Category categoryTwo = Category.builder().categoryName("Clothes").build();

            //Save Category
            categoryRepository.save(categoryOne);
            categoryRepository.save(categoryTwo);


            // Initialize Products
            URL fileURL = getClass().getClassLoader().getResource("static/Products.json");;
            if (fileURL == null) {
                throw new RuntimeException("Products.json file not found in resources/config");
            }
            List<ProductDTO> productDTOList = objectMapper.readValue(
                    fileURL,
                    new TypeReference<>() {}
            );

            //Save Products
            productDTOList.forEach(productDto -> {
                Product product = new Product();
                product.setCategory(categoryOne);
                product.updateProductFromDTO(productDto);
                productRepository.save(product);
            });





        };
    }
}
