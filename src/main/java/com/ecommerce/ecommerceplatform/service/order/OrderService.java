package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Long userId, Order order);
    Order updateOrderStatus(Long orderId, String status);
    void cancelOrder(Long orderId);
    Optional<Order> getOrderById(Long orderId);
    List<Order> getOrdersByUser(Long userId);
    List<Order> getAllOrders();

    void addOrderItem(Long orderId, OrderItem item);
    void removeOrderItem(Long orderId, Long orderItemId);
}
