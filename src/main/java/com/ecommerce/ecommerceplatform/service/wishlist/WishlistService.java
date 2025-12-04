package com.ecommerce.ecommerceplatform.service.wishlist;

import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.entity.Wishlist;

public interface WishlistService {
    Wishlist addItem(WishlistItemRequestDTO wishlistItemRequestDTO, User user);

    Wishlist deleteItem(Long itemId, User user);
}
