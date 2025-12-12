package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.responsedto.CategoryResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO createCategory(String categoryName, Long parentId) throws AccessDeniedException;
    List<ProductResponseDTO> getAllProducts(Long categoryId);
    CategoryResponseDTO findById(Long categoryId);
    void deleteCategory(Long categoryId) throws AccessDeniedException;
}
