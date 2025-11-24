package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.mapper.OrderMapper;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/orders")
public class OrderController {

    OrderService orderService;
    UserServices userServices;
    @Autowired
    public OrderController(OrderService orderService, UserServices userServices) {
        this.orderService = orderService;
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return ResponseEntity.ok(OrderMapper.toDtoList(orderService.getAllOrders(user.getId())));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderSummaryDTO> checkout(Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return ResponseEntity.ok(orderService.checkout(user.getId()));
    }

}
