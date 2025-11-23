package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.CartDTO;
import com.ecommerce.ecommerceplatform.dto.CartItemDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.mapper.CartMapper;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/cart")
public class CartController {

    private final CartService cartService;
    private final UserServices userServices;
    @Autowired
    CartController(CartService cartService, UserServices userServices) {
        this.cartService = cartService;
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return ResponseEntity.ok(CartMapper.toDTO(userServices.getCartByUserID(user.getId())));
    }

    @PostMapping
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItemDTO cartItemDTO, Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return ResponseEntity.ok(CartMapper.toDTO(cartService.addItemToCartByUserID(user.getId()
                                                                    , cartItemDTO.getProductId()
                                                                    , cartItemDTO.getQuantity())));
    }

    @DeleteMapping
    public ResponseEntity<CartDTO> removeItemFromCart(@RequestBody CartItem item,Authentication authentication) {
        String userEmail = authentication.getName();
        var user_op = userServices.getUserByEmail(userEmail);
        if(user_op.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        var user = user_op.get();
        return ResponseEntity.ok(CartMapper.toDTO(cartService.RemoveItemFromCart(user.getId(),item)));
    }
}
