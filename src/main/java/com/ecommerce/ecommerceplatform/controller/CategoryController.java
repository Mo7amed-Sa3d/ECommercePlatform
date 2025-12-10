package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.CategoryRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CategoryResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.dto.mapper.CategoryMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.service.product.CategoryService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;
    private UserUtility userUtility;

    @Autowired
    public void setCategoryService(CategoryService categoryService, UserUtility userUtility) {
        this.categoryService = categoryService;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) throws AccessDeniedException {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(categoryService.createCategory(user,
                                                        categoryRequestDTO.getName(),
                                                         categoryRequestDTO.getParentId()));
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getAllProducts(categoryId));
    }

}
