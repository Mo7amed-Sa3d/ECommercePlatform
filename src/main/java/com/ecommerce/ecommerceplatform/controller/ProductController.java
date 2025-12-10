package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductImageResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.dto.mapper.ProductImageMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController()
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserUtility userUtility;
    @Autowired
    public ProductController(ProductService productService, UserUtility userUtility) {
        this.productService = productService;
        this.userUtility = userUtility;
    }

    /*
        Non-Authenticated end points
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /*
        Authenticated end points
     */

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Seller seller = userUtility.getCurrentUser().getSeller();
        return ResponseEntity.ok().body(productService.saveProduct(ProductMapper.toEntity(productRequestDTO),
                                                                                        productRequestDTO.getBrandId(),
                                                                                        productRequestDTO.getCategoryId()
                                                                                        ,seller));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(productService.findAllByCategoryId(categoryId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId,
                                                            @RequestBody ProductRequestDTO productRequestDTO){
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(productService.updateProduct(user,productId, productRequestDTO));
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<List<String>> uploadProductImage(@PathVariable("productId") Long productId
                                                                     ,@RequestParam("images") List<MultipartFile> imageList) throws IOException {
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(productService.saveProductImage(imageList,user,productId));
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable("productId") Long productId,
                                                           @PathVariable("imageId") Long imageId) throws IOException {
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(productService.deleteProductImage(user,productId, imageId));
    }
    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId) {
        User  user = userUtility.getCurrentUser();
        productService.removeProduct(productId,user);
        return ResponseEntity.ok("Deleted product with id " + productId);
    }
}
