package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Long> {
}
