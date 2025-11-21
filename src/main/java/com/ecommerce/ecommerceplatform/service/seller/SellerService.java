package com.ecommerce.ecommerceplatform.service.seller;

import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Seller createSeller(Seller seller);
    Seller updateSeller(Seller seller);
    void deleteSeller(Long sellerId);
    Optional<Seller> getSellerById(Long sellerId);
    List<Seller> getAllSellers();

    void addProduct(Long sellerId, Product product);
    void removeProduct(Long sellerId, Long productId);
    List<Product> getSellerProducts(Long sellerId);
}
