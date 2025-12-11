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

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getAllWishlist() {
        return ResponseEntity.ok(wishlistService.getWishlist());
    }

    @PostMapping
    public ResponseEntity<WishlistResponseDTO> addWishlistItem(@RequestBody WishlistItemRequestDTO wishlistItemRequestDTO) {
        return ResponseEntity.ok(wishlistService.addItem(wishlistItemRequestDTO));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteWishlistItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(wishlistService.deleteItem(itemId));
    }
}
