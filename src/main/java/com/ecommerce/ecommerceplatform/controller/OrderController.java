package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrdersById());
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderSummaryDTO> checkout(@RequestBody Long addressId) {
        return ResponseEntity.ok(orderService.checkout(addressId));
    }

//    @GetMapping("/shipment/{orderId}")
//    public ResponseEntity<ShipmentResponseDTO> getShipment(@PathVariable Long orderId) {
//        Order order = orderRepository.findById(orderId).get();
//        return ResponseEntity.ok(ShipmentMapper.toDto(order.getShipment()));
//    }
}
