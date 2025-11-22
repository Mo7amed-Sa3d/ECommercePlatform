package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users/{userId}/cart")
public class CartController {

    private final CartService cartService;
    private final UserServices userServices;
    @Autowired
    CartController(CartService cartService, UserServices userServices) {
        this.cartService = cartService;
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(userServices.getCartByUserID(userId));
    }

    @PostMapping
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestBody CartItem item) {
        return ResponseEntity.ok(cartService.addItemToCartByUserID(userId,item));
    }

    @DeleteMapping
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @RequestBody CartItem item) {
        return ResponseEntity.ok(cartService.RemoveItemFromCart(userId,item));
    }
}
