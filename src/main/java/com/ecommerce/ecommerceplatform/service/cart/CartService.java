package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.dto.responsedto.CartItemResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;

public interface CartService {
    CartResponseDTO addItemToCartByUserID(Long userId, Long productId, int quantity);
    CartResponseDTO RemoveItemFromCart(Long userId, Long itemId);
    CartResponseDTO clearCart(Cart cart);
    CartItemResponseDTO gatCartItem(Long itemId);
}
