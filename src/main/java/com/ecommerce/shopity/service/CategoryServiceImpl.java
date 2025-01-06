package com.ecommerce.shopity.service;

import com.ecommerce.shopity.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final List<Category> categories = new ArrayList<>();
    private long nextId = 1L;

    public List<Category> getAllCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        category.setCategoryID(nextId++);
        categories.add(category);
    }


    public String deleteCategory(Long id) {
        Category category = categories.stream()
                .filter(cat -> cat.getCategoryID().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        categories.remove(category);

        return "Category Deleted Successfully: " + id;
    }


    public String updateCategory(Category category, Long id) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(cat -> cat.getCategoryID().equals(id))
                .findFirst();

        if (optionalCategory.isPresent()) {
            Category cat = optionalCategory.get();
            cat.setCategoryName(category.getCategoryName());
            return "Category Updated Successfully: " + id;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        }
    }
}
