package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.mapper.OrderMapper;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/orders")
public class OrderController {

    OrderService orderService;
    UserServices userServices;
    UserUtility userUtility;
    @Autowired
    public OrderController(OrderService orderService,
                           UserServices userServices,
                           UserUtility userUtility) {
        this.orderService = orderService;
        this.userServices = userServices;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication authentication) {
        var user = userUtility.getCurrentUser(authentication);
        return ResponseEntity.ok(OrderMapper.toDtoList(orderService.getAllOrdersById(user.getId())));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderSummaryDTO> checkout(Authentication authentication) {
        var user = userUtility.getCurrentUser(authentication);
        return ResponseEntity.ok(orderService.checkout(user.getId()));
    }

}
