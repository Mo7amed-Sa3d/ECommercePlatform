package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
        return ResponseEntity.ok().body(productService.saveProduct(ProductMapper.toEntity(productRequestDTO),
                                                                                        productRequestDTO.getBrandId(),
                                                                                        productRequestDTO.getCategoryId()));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(productService.findAllByCategoryId(categoryId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId,
                                                            @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(productId, productRequestDTO));
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<List<String>> uploadProductImage(@PathVariable("productId") Long productId
                                                                     ,@RequestParam("images") List<MultipartFile> imageList) throws IOException {
        return ResponseEntity.ok(productService.saveProductImage(imageList,productId));
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable("productId") Long productId,
                                                           @PathVariable("imageId") Long imageId) throws IOException {
        return ResponseEntity.ok(productService.deleteProductImage(productId, imageId));
    }
    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId) {
        productService.removeProduct(productId);
        return ResponseEntity.ok("Deleted product with id " + productId);
    }
}
