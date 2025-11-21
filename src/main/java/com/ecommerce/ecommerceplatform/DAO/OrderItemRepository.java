package com.ecommerce.ecommerceplatform.DAO;

import com.ecommerce.ecommerceplatform.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
