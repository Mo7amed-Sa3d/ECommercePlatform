package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.CategoryResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        if(category.getParent() != null)
            categoryResponseDTO.setParentId(category.getParent().getId());
        else
            category.setParent(null);
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
