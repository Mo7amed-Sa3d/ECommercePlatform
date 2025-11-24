package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartItemResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static Cart toEntity(CartResponseDTO cartResponseDTO,
                                User user,
                                List<CartItem> cartItems
                                 ) {
        Cart cart = new Cart();
        cart.setId(cartResponseDTO.getId());
        cart.setUpdatedAt(cartResponseDTO.getUpdatedAt());
        cart.setUser(user);
        cart.setCartItems(cartItems);
        return cart;
    }

    public static CartResponseDTO toDTO(Cart cart) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId(cart.getId());
        cartResponseDTO.setUpdatedAt(cart.getUpdatedAt());
        cartResponseDTO.setUserID(cart.getUser().getId());
        List<CartItemResponseDTO> cartItemResponseDTOList = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()) {
            CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
            cartItemResponseDTO.setId(cartItem.getId());
            cartItemResponseDTO.setQuantity(cartItem.getQuantity());
            cartItemResponseDTO.setProductId(cartItem.getProduct().getId());
            cartItemResponseDTO.setCartId(cartItem.getCart().getId());
            cartItemResponseDTOList.add(cartItemResponseDTO);
        }
        cartResponseDTO.setCartItemResponseDTOList(cartItemResponseDTOList);
        return cartResponseDTO;
    }
}
