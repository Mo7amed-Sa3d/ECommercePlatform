package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(String categoryName,Long parentId) {
        Category parent = categoryRepository.findById(parentId).get();
        Category category = new Category();
        category.setName(categoryName);
        parent.addChild(category);
        return categoryRepository.save(category);
    }
}
