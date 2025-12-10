package com.ecommerce.ecommerceplatform.service.wishlist;

import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.WishlistResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;

public interface WishlistService {
    WishlistResponseDTO addItem(WishlistItemRequestDTO wishlistItemRequestDTO, User user);

    String deleteItem(Long itemId, User user);
}
