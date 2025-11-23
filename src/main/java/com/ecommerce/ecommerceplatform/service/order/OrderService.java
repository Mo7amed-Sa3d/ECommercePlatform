package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user);
    OrderSummaryDTO checkout(Long userId);
    List<Order> getAllOrders(Long userId);
}
