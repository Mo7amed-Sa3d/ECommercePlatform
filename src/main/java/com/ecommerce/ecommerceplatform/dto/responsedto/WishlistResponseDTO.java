package com.ecommerce.ecommerceplatform.dto.responsedto;

import com.ecommerce.ecommerceplatform.entity.WishlistItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WishlistResponseDTO {
    private List<WishlistItemResponseDTO> wishlistItems;
}
