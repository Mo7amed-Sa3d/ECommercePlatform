package com.ecommerce.ecommerceplatform.service.wishlist;

import com.ecommerce.ecommerceplatform.dto.mapper.WishlistItemMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.WishlistItemRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.entity.Wishlist;
import com.ecommerce.ecommerceplatform.entity.WishlistItem;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import com.ecommerce.ecommerceplatform.repository.WishlistItemRepository;
import com.ecommerce.ecommerceplatform.repository.WishlistRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistServiceImplementation implements WishlistService {

    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    public WishlistServiceImplementation(WishlistRepository wishlistRepository, ProductRepository productRepository, WishlistItemRepository wishlistItemRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional
    public Wishlist addItem(WishlistItemRequestDTO wishlistItemRequestDTO, User user) {
        var optional = productRepository.findById(wishlistItemRequestDTO.getProductId());
        if (optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        var product = optional.get();

        if(user.getWishlist() == null){
            user.setWishlist(new Wishlist());

        }
        Wishlist wishlist = user.getWishlist();
        wishlist.setName("Wishlist");

        for(var item : wishlist.getWishlistItems()){
            if(item.getProduct().getId().equals(product.getId())){
                throw new EntityExistsException("Product already exists");
            }
        }

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(product);
        wishlistItem.setWishlist(wishlist);

        return wishlistRepository.save(user.getWishlist());
    }

    @Override
    @Transactional
    public String deleteItem(Long itemId, User user) {
        var optional = wishlistItemRepository.findById(itemId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Item not found");
        WishlistItem item = optional.get();
        item.getWishlist().getWishlistItems().remove(item);
        wishlistItemRepository.delete(item);
        return "Done deleting item with id " + item.getId();
    }

}
