package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.CartDTO;
import com.ecommerce.ecommerceplatform.dto.CartItemDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static Cart toEntity(CartDTO cartDTO,
                                 User user,
                                 List<CartItem> cartItems
                                 ) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setUpdatedAt(cartDTO.getUpdatedAt());
        cart.setUser(user);
        cart.setCartItems(cartItems);
        return cart;
    }

    public static CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());
        cartDTO.setUserID(cart.getUser().getId());
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setProductId(cartItem.getProduct().getId());
            cartItemDTO.setCartId(cartItem.getCart().getId());
            cartItemDTOList.add(cartItemDTO);
        }
        cartDTO.setCartItemDTOList(cartItemDTOList);
        return cartDTO;
    }
}
