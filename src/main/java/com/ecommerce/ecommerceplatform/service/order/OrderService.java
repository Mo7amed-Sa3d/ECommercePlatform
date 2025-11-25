package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.Shipment;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user);
    OrderSummaryDTO checkout(Long userId,Long addressId);
    List<Order> getAllOrdersById(Long userId);
    Shipment createShipment(Long addressId,Order order);

    Order findById(Long orderId);
}
