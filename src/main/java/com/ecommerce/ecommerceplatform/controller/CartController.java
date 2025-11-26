package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.CartItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.mapper.CartMapper;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users/cart")
public class CartController {

    private final CartService cartService;
    private final UserUtility userUtility;
    @Autowired
    CartController(CartService cartService, UserUtility userUtility) {
        this.cartService = cartService;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() throws ResponseStatusException {
        var user = userUtility.getCurrentUser();
        if (user.getCart() == null || user.getCart().getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        return ResponseEntity.ok(CartMapper.toDTO(user.getCart()));
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO) {

        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(CartMapper.toDTO(cartService.addItemToCartByUserID(user.getId()
                                                                    , cartItemRequestDTO.getProductId()
                                                                    , cartItemRequestDTO.getQuantity())));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(@PathVariable Long itemId) {
        var user = userUtility.getCurrentUser();
        //TODO: improve: move getCartItem inside the service layer and throw exception if not found
        CartItem item = cartService.gatCartItem(itemId);
        return ResponseEntity.ok(CartMapper.toDTO(cartService.RemoveItemFromCart(user.getId(),item)));
    }
}
