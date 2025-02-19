package com.ecommerce.shopity.service;

import com.ecommerce.shopity.payload.ProductDTO;
import com.ecommerce.shopity.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO addProduct(ProductDTO product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductByCategory(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);

    ProductDTO updateProductById(ProductDTO product, Long productId);

    void deleteProductById(Long productId);

    void udpateProductImage(Long productId, MultipartFile image);
}
