package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.dto.mapper.ShipmentMapper;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserUtility userUtility;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService,
                           UserUtility userUtility, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.userUtility = userUtility;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(orderService.getAllOrdersById(user.getId()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderSummaryDTO> checkout(@RequestBody Long addressId) {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(orderService.checkout(user.getId(),addressId));
    }

    @GetMapping("/shipment/{orderId}")
    public ResponseEntity<ShipmentResponseDTO> getShipment(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return ResponseEntity.ok(ShipmentMapper.toDto(order.getShipment()));
    }
}
