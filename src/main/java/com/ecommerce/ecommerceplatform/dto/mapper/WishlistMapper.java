package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.WishlistResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Wishlist;
import com.ecommerce.ecommerceplatform.entity.WishlistItem;

import java.util.List;

public class WishlistMapper {
    public static WishlistResponseDTO toDTO(Wishlist wishlist) {
        WishlistResponseDTO wishlistResponseDTO = new WishlistResponseDTO();

        List<WishlistItem>  listItems = wishlist.getWishlistItems();
        for (WishlistItem wishlistItem : listItems) {
            wishlistResponseDTO.getWishlistItems().add(WishlistItemMapper.toDto(wishlistItem));
        }
        return wishlistResponseDTO;
    }
}
