package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.mapper.WishlistMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.WishlistResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.service.wishlist.WishlistService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/wishlist")
public class WishlistController {

    private final UserUtility userUtility;
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService, UserUtility userUtility) {
        this.wishlistService = wishlistService;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getAllWishlist() {
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(WishlistMapper.toDTO(user.getWishlist()));
    }

    @PostMapping
    public ResponseEntity<WishlistResponseDTO> addWishlistItem(@RequestBody WishlistItemRequestDTO wishlistItemRequestDTO) {
        User user = userUtility.getCurrentUser();
        var wishlist = wishlistService.addItem(wishlistItemRequestDTO,user);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteWishlistItem(@PathVariable Long itemId) {
        User user = userUtility.getCurrentUser();
        String wishlist = wishlistService.deleteItem(itemId,user);
        return ResponseEntity.ok(wishlist);
    }
}
