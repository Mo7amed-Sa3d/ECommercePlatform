package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;

public interface CartService {
    Cart addItemToCartByUserID(Long userId, Long productId, int quantity);
    Cart RemoveItemFromCart(Long userId, CartItem item);
    Cart clearCart(Cart cart);
}
