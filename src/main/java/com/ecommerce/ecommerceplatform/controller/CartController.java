package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.CartItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() throws ResponseStatusException {
        return ResponseEntity.ok(cartService.getUserCart());
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartService.addItemToCartByUserID(cartItemRequestDTO.getProductId()
                                                                    , cartItemRequestDTO.getQuantity()));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.RemoveItemFromCart(itemId));
    }
}
