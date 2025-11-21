package com.ecommerce.ecommerceplatform.DAO;

import com.ecommerce.ecommerceplatform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
