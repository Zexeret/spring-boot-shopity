package com.ecommerce.shopity.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> content ;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNumber ;
    private Integer pageSize ;
    private boolean isLastPage;
}
