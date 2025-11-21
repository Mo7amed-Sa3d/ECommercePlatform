package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long categoryId);
    Optional<Category> getCategoryById(Long categoryId);
    List<Category> getAllCategories();

    void addProductToCategory(Long categoryId, Long productId);
    void removeProductFromCategory(Long categoryId, Long productId);
    List<Product> getCategoryProducts(Long categoryId);

    void addChildCategory(Long parentId, Category child);
    void removeChildCategory(Long parentId, Long childId);
}
