package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
