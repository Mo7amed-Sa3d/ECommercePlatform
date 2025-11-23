package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.CategoryDTO;
import com.ecommerce.ecommerceplatform.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO categoryDTO,Category parent) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setParent(parent);
        return category;
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setParentId(category.getParent().getId());
        return categoryDTO;
    }

    public static List<CategoryDTO> toDTOList(List<Category> all) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : all) {
            categoryDTOList.add(CategoryMapper.toDTO(category));
        }
        return categoryDTOList;
    }
}
