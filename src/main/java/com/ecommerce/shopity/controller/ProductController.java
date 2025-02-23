package com.ecommerce.shopity.controller;

import com.ecommerce.shopity.payload.ProductDTO;
import com.ecommerce.shopity.payload.ProductResponse;
import com.ecommerce.shopity.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/public/product")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO product,
                                                 @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.getProductByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable String keyword) {
        ProductResponse productResponse = productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/public/product/{productId}")
    public ResponseEntity<ProductDTO> updateProductById(@Valid @RequestBody ProductDTO product, @PathVariable Long productId) {
        ProductDTO productDTO = productService.updateProductById(product, productId);
        return new ResponseEntity<>(productDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/public/product/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted with id: " + productId, HttpStatus.OK);
    }

    @PutMapping("/product/{productId}/image")
    public ResponseEntity<String> udpateProductImage(@PathVariable Long productId,
                                                     @RequestParam("Image") MultipartFile image) {

        if (image.isEmpty()) {
            return new ResponseEntity<>("Image is empty", HttpStatus.BAD_REQUEST);
        }


        productService.udpateProductImage(productId, image);
        return new ResponseEntity<>("Image uploaded to product with id: " + productId, HttpStatus.OK);
    }


}
