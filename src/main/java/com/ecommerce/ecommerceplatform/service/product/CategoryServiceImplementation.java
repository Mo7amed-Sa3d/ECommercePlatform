package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CategoryRepository;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository,ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category createCategory(User user, String categoryName, Long parentId) throws AccessDeniedException {
        if(!user.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("You are not allowed to access this category");
        Category category = new Category();
        if(parentId != null) {
            var parentOptional = categoryRepository.findById(parentId);
            parentOptional.ifPresent(parent -> parent.addChild(category));
        }
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> getAllProducts(Long categoryId) {
        return productRepository.findAllProductsByCategory(categoryId);
    }

    @Override
    public Category findById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty())
            throw new EntityNotFoundException("Category not found");
        return category.get();
    }
}
