package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Order> getOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return orderService.getAllOrders(user.getId());
    }

    @PostMapping("/checkout")
    public OrderSummaryDTO checkout(Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return orderService.checkout(user.getId());
    }

}
