package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.CartItemResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.Product;

public class CartItemMapper {

    public static CartItemResponseDTO toDTO(CartItem cartItem) {
        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
        cartItemResponseDTO.setId(cartItem.getId());
        cartItemResponseDTO.setProductId(cartItem.getProduct().getId());
        cartItemResponseDTO.setQuantity(cartItem.getQuantity());
        cartItemResponseDTO.setCartId(cartItem.getCart().getId());
        return cartItemResponseDTO;
    }
}
