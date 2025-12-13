package com.ecommerce.ecommerceplatform.service.wishlist;

import com.ecommerce.ecommerceplatform.configuration.cache.CacheNames;
import com.ecommerce.ecommerceplatform.dto.mapper.WishlistMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.WishlistResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.entity.Wishlist;
import com.ecommerce.ecommerceplatform.entity.WishlistItem;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import com.ecommerce.ecommerceplatform.repository.WishlistItemRepository;
import com.ecommerce.ecommerceplatform.repository.WishlistRepository;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistServiceImplementation implements WishlistService {

    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserUtility userUtility;

    public WishlistServiceImplementation(WishlistRepository wishlistRepository,
                                         ProductRepository productRepository,
                                         WishlistItemRepository wishlistItemRepository,
                                         UserUtility userUtility) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.userUtility = userUtility;
    }

    // -------------------------------------------------------------------------
    // Public Methods
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    @CachePut(value = CacheNames.wishlists, key = "#userUtility.getCurrentUser().id")
    public WishlistResponseDTO addItem(WishlistItemRequestDTO dto) {
        User user = userUtility.getCurrentUser();
        Product product = findProductByIdOrThrow(dto.getProductId());
        Wishlist wishlist = getOrCreateWishlist(user);

        ensureProductNotInWishlist(wishlist, product);

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(product);
        wishlistItem.setWishlist(wishlist);

        return WishlistMapper.toDTO(wishlistRepository.save(wishlist));
    }

    @Override
    @Cacheable(value = CacheNames.wishlists, key = "#userUtility.getCurrentUser().id")
    public WishlistResponseDTO getWishlist() {
        User user = userUtility.getCurrentUser();
        return WishlistMapper.toDTO(user.getWishlist());
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.wishlists, key = "#userUtility.getCurrentUser().id")
    public String deleteItem(Long itemId) {
        WishlistItem item = findWishlistItemByIdOrThrow(itemId);
        item.getWishlist().getWishlistItems().remove(item);
        wishlistItemRepository.delete(item);
        return "Done deleting item with id " + item.getId();
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    private Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private WishlistItem findWishlistItemByIdOrThrow(Long itemId) {
        return wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
    }

    private Wishlist getOrCreateWishlist(User user) {
        Wishlist wishlist = user.getWishlist();
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setName("Wishlist");
            user.setWishlist(wishlist);
        }
        return wishlist;
    }

    private void ensureProductNotInWishlist(Wishlist wishlist, Product product) {
        boolean exists = wishlist.getWishlistItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(product.getId()));
        if (exists) {
            throw new EntityExistsException("Product already exists in wishlist");
        }
    }
}
