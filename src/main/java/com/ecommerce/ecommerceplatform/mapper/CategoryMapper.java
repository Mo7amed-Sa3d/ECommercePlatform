package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.CategoryResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category toEntity(CategoryResponseDTO categoryResponseDTO, Category parent) {
        Category category = new Category();
        category.setId(categoryResponseDTO.getId());
        category.setName(categoryResponseDTO.getName());
        category.setParent(parent);
        return category;
    }

    public static CategoryResponseDTO toDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setParentId(category.getParent().getId());
        return categoryResponseDTO;
    }

    public static List<CategoryResponseDTO> toDTOList(List<Category> all) {
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        for (Category category : all) {
            categoryResponseDTOList.add(CategoryMapper.toDTO(category));
        }
        return categoryResponseDTOList;
    }
}
