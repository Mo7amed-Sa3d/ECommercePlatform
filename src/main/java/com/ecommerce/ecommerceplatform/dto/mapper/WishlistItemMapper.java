package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.WishlistItemResponseDTO;
import com.ecommerce.ecommerceplatform.entity.WishlistItem;

public class WishlistItemMapper {


    public static WishlistItemResponseDTO toDto(WishlistItem wishlistItem) {
        WishlistItemResponseDTO dto = new WishlistItemResponseDTO();
        dto.setProductId(wishlistItem.getProduct().getId());
        dto.setVariantId(wishlistItem.getProductVariant().getId());
        return dto;
    }

}
