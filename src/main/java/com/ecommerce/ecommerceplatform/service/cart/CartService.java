package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;

import java.util.List;

public interface CartService {
    Cart getCartByUserId(Long userId);
    void addItemToCart(Long userId, CartItem item);
    void removeItemFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
    List<CartItem> getCartItems(Long userId);
}
