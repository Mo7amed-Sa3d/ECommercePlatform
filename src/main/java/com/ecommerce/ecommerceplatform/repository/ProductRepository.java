package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    /*
        @Query(
        value = "SELECT p.* FROM product p JOIN product_category pc ON p.id = pc.product_id WHERE pc.category_id = :categoryId",
        nativeQuery = true
    )

     */

    @Query("select p from Product p join Category c where c.id = :categoryId")
    List<Product> findAllProductsByCategory(@Param("categoryId") Long categoryId);
}
