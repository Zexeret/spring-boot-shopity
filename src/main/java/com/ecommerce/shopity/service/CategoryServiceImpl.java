package com.ecommerce.shopity.service;

import com.ecommerce.shopity.exceptions.APIException;
import com.ecommerce.shopity.exceptions.ResourceNotFoundException;
import com.ecommerce.shopity.model.Category;
import com.ecommerce.shopity.payload.CategoryDTO;
import com.ecommerce.shopity.payload.CategoryResponse;
import com.ecommerce.shopity.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final ObjectMapper modelMapper;


    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No Categories Found!");
        }
        return categories;
    }


    public CategoryResponse getPaginatedCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        if(pageNumber < 1 || pageSize < 1){
            throw new APIException("Invalid Page Number or Page Size") ;
        }
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.ofSize(pageSize).withPage(pageNumber - 1).withSort(sortByAndOrder);
        
        Page<Category> pageCategories = categoryRepository.findAll(pageDetails);
        List<Category> categories = pageCategories.getContent();

        if (pageCategories.isEmpty()) {
            throw new APIException("No Categories Found!");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(this::mapToDTO)
                .toList();

        CategoryResponse categoriesResponse = new CategoryResponse();
        categoriesResponse.setContent(categoryDTOS);
        categoriesResponse.setPageNumber(pageNumber);
        categoriesResponse.setPageSize(pageSize);
        categoriesResponse.setTotalElements(pageCategories.getTotalElements());
        categoriesResponse.setTotalPages(pageCategories.getTotalPages());
        categoriesResponse.setLastPage(pageCategories.isLast());

        return categoriesResponse;
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName()) ;

        if(existingCategory.isPresent()){
            throw new APIException("Category with the name '" + categoryDTO.getCategoryName() + "' already exists!") ;
        }

        Category category = mapToEntity(categoryDTO);

        Category savedCategory = categoryRepository.save(category);

        return mapToDTO(savedCategory);
    }


    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", id));
        categoryRepository.deleteById(id);

        return mapToDTO(category);
    }


    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(id) ;

        Category savedCategory = savedCategoryOptional.orElseThrow(() ->  new ResourceNotFoundException("category", "categoryId", id)) ;

        savedCategory.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(savedCategory);

        return mapToDTO(savedCategory);

    }

    private CategoryDTO mapToDTO(Category category) {
        return modelMapper.convertValue(category, CategoryDTO.class);
    }
    private Category mapToEntity(CategoryDTO categoryDTO) {
        return modelMapper.convertValue(categoryDTO, Category.class);
    }
}

