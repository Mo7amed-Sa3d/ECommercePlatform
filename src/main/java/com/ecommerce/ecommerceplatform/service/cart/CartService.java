package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;

import java.util.List;

public interface CartService {
    Cart addItemToCartByUserID(Long userId, CartItem item);

    Cart RemoveItemFromCart(Long userId, CartItem item);
}
