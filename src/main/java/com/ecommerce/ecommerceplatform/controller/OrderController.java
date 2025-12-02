package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.dto.mapper.OrderMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.ShipmentMapper;
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
    @Autowired
    public OrderController(OrderService orderService,
                           UserUtility userUtility) {
        this.orderService = orderService;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(OrderMapper.toDtoList(orderService.getAllOrdersById(user.getId())));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderSummaryDTO> checkout(@RequestBody Long addressId) {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(orderService.checkout(user.getId(),addressId));
    }

    @GetMapping("/shipment/{orderId}")
    public ResponseEntity<ShipmentResponseDTO> getShipment(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok(ShipmentMapper.toDto(order.getShipment()));
    }
}
