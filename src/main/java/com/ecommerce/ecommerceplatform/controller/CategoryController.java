package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.CategoryDTO;
import com.ecommerce.ecommerceplatform.entity.Category;
import com.ecommerce.ecommerceplatform.mapper.CategoryMapper;
import com.ecommerce.ecommerceplatform.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(CategoryMapper.toDTOList(categoryService.getAllCategories()));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody String categoryName,Long parentId){
        return ResponseEntity.ok(CategoryMapper.toDTO(categoryService.createCategory(categoryName,parentId)));
    }

}
