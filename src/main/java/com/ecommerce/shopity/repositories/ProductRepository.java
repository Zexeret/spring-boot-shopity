package com.ecommerce.shopity.repositories;

import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
