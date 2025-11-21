package com.ecommerce.ecommerceplatform.DAO;

import com.ecommerce.ecommerceplatform.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
}
