package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query(
        value = "SELECT p.* FROM product p JOIN product_category pc ON p.id = pc.product_id WHERE pc.category_id = :categoryId and p.active=true",
        nativeQuery = true
    )
    List<Product> findAllProductsByCategory(@Param("categoryId") Long categoryId);

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

}
