package com.ecommerce.shopity.service;

import com.ecommerce.shopity.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void addCategory(Category category);

    String deleteCategory(Long id);

    String updateCategory(Category category,Long id);
}
