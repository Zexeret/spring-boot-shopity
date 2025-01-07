package com.ecommerce.shopity.service;

import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.payload.CategoryDTO;
import com.ecommerce.shopity.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    CategoryResponse getPaginatedCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) ;

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO categoryDTO,Long id);
}
