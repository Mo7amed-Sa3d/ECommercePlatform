package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.ProductImage;
import com.ecommerce.ecommerceplatform.entity.ProductVariant;
import com.ecommerce.ecommerceplatform.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);
    Optional<Product> getProductById(Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsBySeller(Long sellerId);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsByBrand(Long brandId);

    // Variants
    ProductVariant addVariant(Long productId, ProductVariant variant);
    void removeVariant(Long variantId);

    // Images
    ProductImage addImage(Long productId, ProductImage image);
    void removeImage(Long imageId);

    // Reviews
    List<Review> getProductReviews(Long productId);
}
