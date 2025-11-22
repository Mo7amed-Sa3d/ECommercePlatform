package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.OrderSummary;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/users/{userId}/orders")
public class OrderController {

    OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderService.getAllOrders(userId);
    }

    @PostMapping("/checkout")
    public OrderSummary checkout(@PathVariable Long userId) {
        return orderService.checkout(userId);
    }

}
