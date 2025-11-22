package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.OrderSummary;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(User user);
    OrderSummary checkout(Long userId);
    List<Order> getAllOrders(Long userId);
}
