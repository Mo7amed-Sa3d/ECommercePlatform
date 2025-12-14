package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user);
    OrderSummaryDTO checkout(Long addressId);
    List<OrderResponseDTO> getAllOrdersById();
//    ShipmentResponseDTO createShipment(Long addressId, Order order);
    OrderResponseDTO findById(Long orderId);
    void markOrderPaid(Long id,String PaymentId);
}
