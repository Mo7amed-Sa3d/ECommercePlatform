package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.configuration.cache.CacheNames;
import com.ecommerce.ecommerceplatform.dto.mapper.CategoryMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.dto.responsedto.CategoryResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CategoryRepository;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserUtility userUtility;

    public CategoryServiceImplementation(CategoryRepository categoryRepository,
                                         ProductRepository productRepository,
                                         UserUtility userUtility) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userUtility = userUtility;
    }

    // ---------------------------------------------------------------------
    // Public Service Methods
    // ---------------------------------------------------------------------

    @Override
    @Cacheable(value = CacheNames.categoryList)
    public List<CategoryResponseDTO> getAllCategories() {
        return CategoryMapper.toDTOList(categoryRepository.findAll());
    }

    @Override
    @Transactional
    @Caching(
            put = @CachePut(value = CacheNames.categories, key = "#result.id"),
            evict = {
                    @CacheEvict(value = CacheNames.categoryList, allEntries = true),
                    @CacheEvict(value = CacheNames.productsInCategory, allEntries = true)
            }
    )
    public CategoryResponseDTO createCategory(String categoryName, Long parentId) throws AccessDeniedException {
        ensureCurrentUserIsAdmin();

        Category category = new Category();
        attachParentCategoryIfExists(category, parentId);

        category.setName(categoryName);

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    @Override
    @Cacheable(value = CacheNames.productsInCategory,key = "#categoryId")
    public List<ProductResponseDTO> getAllProducts(Long categoryId) {
        return ProductMapper.toDTOList(productRepository.findAllProductsByCategory(categoryId));
    }

    @Override
    @Cacheable(value = CacheNames.categories,key = "#categoryId")
    public CategoryResponseDTO findById(Long categoryId) {
        Category category = getCategoryOrThrow(categoryId);
        return CategoryMapper.toDTO(category);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CacheNames.categoryList, allEntries = true),
                    @CacheEvict(value = CacheNames.productsInCategory, key = "#categoryId")
            }
    )
    public void deleteCategory(Long categoryId) throws AccessDeniedException {
        User user = userUtility.getCurrentUser();
        if (!"ROLE_ADMIN".equals(user.getRole())) {
            throw new AccessDeniedException("You are not allowed to delete this category");
        }
        for(var category : categoryRepository.findAll()) {
            category.setParent(null);
        }
        Category category = getCategoryOrThrow(categoryId);
        categoryRepository.delete(category);
    }

    // ---------------------------------------------------------------------
    // Helper Methods
    // ---------------------------------------------------------------------

    private void ensureCurrentUserIsAdmin() throws AccessDeniedException {
        var user = userUtility.getCurrentUser();
        if (!"ROLE_ADMIN".equals(user.getRole())) {
            throw new AccessDeniedException("You are not allowed to access this category");
        }
    }

    private Category getCategoryOrThrow(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        return category.get();
    }

    private void attachParentCategoryIfExists(Category category, Long parentId) {
        if (parentId == null) {
            return;
        }

        Optional<Category> parentOptional = categoryRepository.findById(parentId);
        parentOptional.ifPresent(parent -> parent.addChild(category));
    }
}
