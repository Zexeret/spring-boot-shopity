package com.ecommerce.shopity.service;

import com.ecommerce.shopity.exceptions.ResourceNotFoundException;
import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.model.Product;
import com.ecommerce.shopity.payload.ProductDTO;
import com.ecommerce.shopity.payload.ProductResponse;
import com.ecommerce.shopity.repositories.CategoryRepository;
import com.ecommerce.shopity.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;


    public ProductDTO addProduct(ProductDTO product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Product freshProduct = convertDtoToClass(product);

        freshProduct.setSpecialPrice(product.getPrice() - (product.getDiscount()/100)* product.getPrice());
        freshProduct.setCategory(category);

       return mapToDto(productRepository.save(freshProduct));
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream().map(this::mapToDto).toList();

        return ProductResponse.builder().content(productDTOS).build();
    }

    @Override
    public ProductResponse getProductByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Product> products = productRepository.findByCategory(category) ;

        List<ProductDTO> productDTOS = products.stream().map(this::mapToDto).toList();

        return ProductResponse.builder().content(productDTOS).build();
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword) {

        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword) ;

        List<ProductDTO> productDTOS = products.stream().map(this::mapToDto).toList();

        return ProductResponse.builder().content(productDTOS).build();
    }

    @Override
    public ProductDTO updateProductById(ProductDTO product, Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId) ;
        if (existingProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        Product savedProduct = existingProduct.get();
        savedProduct.updateProductFromDTO(product);
        savedProduct.setProductId(productId);
        return mapToDto(productRepository.save(savedProduct));
    }

    @Override
    public void deleteProductById(Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        existingProduct.ifPresent(productRepository::delete);

    }

    @Override
    public void udpateProductImage(Long productId, MultipartFile image) {
        // 1- Get the product from DB
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Upload image to server



    }


    private ProductDTO mapToDto(Product product) {
        return objectMapper.convertValue(product, ProductDTO.class);
    }

    private Product convertDtoToClass(ProductDTO product) {
        return objectMapper.convertValue(product, Product.class);
    }
}

