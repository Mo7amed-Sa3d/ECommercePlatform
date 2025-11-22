package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.ProductImage;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import com.ecommerce.ecommerceplatform.service.seller.SellerService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
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
    SellerService sellerService;
    @Autowired
    public ProductController(ProductService productService, SellerService sellerService) {
        this.productService = productService;
        this.sellerService = sellerService;
    }

    /*
        Non-Authenticated end points
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /*
        Authenticated end points
     */

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, Authentication authentication) {
        String sellerEmail = authentication.getName();
        Seller seller = sellerService.findSellerByEmail(sellerEmail);
        return ResponseEntity.ok().body(productService.saveProduct(product,seller));
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<ProductImage> uploadProductImage(@PathVariable("productId") Long productId,
                                                           @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.saveProductImage(file,productId));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable("productId") Long productId) {
        productService.removeProduct(productId);
        return ResponseEntity.ok("Deleted product with id " + productId);
    }
}
