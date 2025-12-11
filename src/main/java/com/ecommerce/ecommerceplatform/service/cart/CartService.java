package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.dto.responsedto.CartItemResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;

public interface CartService {
    CartResponseDTO addItemToCartByUserID(Long productId, int quantity);
    CartResponseDTO RemoveItemFromCart(Long itemId);
    CartResponseDTO clearCart(Cart cart);
    CartItemResponseDTO gatCartItem(Long itemId);
    CartResponseDTO getUserCart();
}
