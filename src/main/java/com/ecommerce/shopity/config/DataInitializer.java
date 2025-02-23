package com.ecommerce.shopity.config;


import com.ecommerce.shopity.model.*;
import com.ecommerce.shopity.payload.ProductDTO;
import com.ecommerce.shopity.repositories.CategoryRepository;
import com.ecommerce.shopity.repositories.ProductRepository;
import com.ecommerce.shopity.repositories.RoleRepository;
import com.ecommerce.shopity.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URL;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
            URL fileURL = getClass().getClassLoader().getResource("static/Products.json");

            if (fileURL == null) {
                throw new RuntimeException("Products.json file not found in resources/config");
            }
            List<ProductDTO> productDTOList = objectMapper.readValue(
                    fileURL,
                    new TypeReference<>() {
                    }
            );

            //Save Products
            productDTOList.forEach(productDto -> {
                Product product = new Product();
                product.setCategory(categoryOne);
                product.updateProductFromDTO(productDto);
                productRepository.save(product);
            });


            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = User.builder()
                        .userName("user1")
                        .password(passwordEncoder.encode("password1"))
                        .email("user1@example.com")
                        .build();
               userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = User.builder().userName("seller1").password(passwordEncoder.encode("password2")).email("seller1@example.com").build();
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = User.builder()
                        .userName("admin").password(passwordEncoder.encode("adminPass"))
                        .email("admin@example.com")
                        .build();
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
        };
    }
}
