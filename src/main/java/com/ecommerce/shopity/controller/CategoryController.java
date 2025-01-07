package com.ecommerce.shopity.controller;

import com.ecommerce.shopity.config.AppConstants;
import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.payload.CategoryDTO;
import com.ecommerce.shopity.payload.CategoryResponse;
import com.ecommerce.shopity.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/allcategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getPaginatedCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORY_DIR , required = false) String sortOrder
    ) {
        CategoryResponse categoryResponse = categoryService.getPaginatedCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO responseDTO =  categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/public/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO responseDTO = categoryService.deleteCategory(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }

    @PutMapping("/public/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category, @PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(categoryDTO);
    }
}

