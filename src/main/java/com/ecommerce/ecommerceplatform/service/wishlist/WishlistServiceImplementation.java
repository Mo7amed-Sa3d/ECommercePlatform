package com.ecommerce.ecommerceplatform.service.wishlist;

import com.ecommerce.ecommerceplatform.dto.mapper.WishlistItemMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.entity.Wishlist;
import com.ecommerce.ecommerceplatform.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImplementation implements WishlistService {

    WishlistRepository wishlistRepository;
    public WishlistServiceImplementation(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    //TODO: Implement
    @Override
    public Wishlist addItem(WishlistItemRequestDTO wishlistItemRequestDTO, User user) {
        return null;
    }

    //TODO: Implement
    @Override
    public Wishlist deleteItem(Long itemId, User user) {
        return null;
    }

}
