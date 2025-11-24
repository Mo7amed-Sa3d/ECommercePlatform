package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductImageResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.mapper.ProductImageMapper;
import com.ecommerce.ecommerceplatform.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import com.ecommerce.ecommerceplatform.service.seller.SellerService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserUtility userUtility;
    SellerService sellerService;
    @Autowired
    public ProductController(ProductService productService, SellerService sellerService, UserUtility userUtility) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.userUtility = userUtility;
    }

    /*
        Non-Authenticated end points
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(ProductMapper.toDTOList(productService.getAllProducts()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ProductMapper.toDTO(productService.getProductById(productId)));
    }

    /*
        Authenticated end points
     */

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO, Authentication authentication) {
        Seller seller = userUtility.getCurrentUser(authentication).getSeller();

        return ResponseEntity.ok().body(ProductMapper.toDTO(productService.saveProduct(ProductMapper.toEntity(productRequestDTO),
                                                                                        productRequestDTO.getBrandId(),
                                                                                        productRequestDTO.getCategoryId()
                                                                                        ,seller)));
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<ProductImageResponseDTO> uploadProductImage(@PathVariable("productId") Long productId,
                                                                      @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(ProductImageMapper.toDTO(productService.saveProductImage(file,productId)));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId) {
        productService.removeProduct(productId);
        return ResponseEntity.ok("Deleted product with id " + productId);
    }
}
