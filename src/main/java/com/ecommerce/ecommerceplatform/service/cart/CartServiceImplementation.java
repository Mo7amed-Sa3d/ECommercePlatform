package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CartItemRepository;
import com.ecommerce.ecommerceplatform.repository.CartRepository;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImplementation implements CartService {

    CartRepository cartRepository;
    ProductService productService;
    UserServices userServices;
    CartItemRepository cartItemRepository;
    @Autowired
    public CartServiceImplementation(CartRepository cartRepository,
                                     UserServices userServices,
                                     ProductService productService,
                                     CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userServices = userServices;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    @Transactional
    public Cart addItemToCartByUserID(Long userId, Long productId, int quantity) {
        Cart cart = userServices.getCartByUserID(userId);
        if(cart == null) {
            //TODO: Make this throw exception
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            User user = userServices.getUserByID(userId);
            cart.setUser(user);
        }
        cart.setUpdatedAt(Instant.now());
        boolean itemExists = false;
        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().getId().equals(productId)) {
                itemExists = true;
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                if(cartItem.getQuantity() <= 0) {
                    RemoveItemFromCart(userId,cartItem);
                }
                break;
            }
        }
        if(!itemExists) {
            CartItem item = new CartItem();
            item.setProduct(productService.getProductById(productId));
            item.setQuantity(quantity);
            cart.addCartItem(item);
        }
        return cart;
    }

    @Override
    @Transactional
    public Cart RemoveItemFromCart(Long userId, CartItem item) {
        Cart cart = userServices.getCartByUserID(userId);
        if(cart == null || cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        cart.removeCartItem(item);
        return cartRepository.save(cart);
    }


    //TODO: Delete the cart_item from the database not only breaking the link

    @Override
    @Transactional
    public Cart clearCart(Cart cart) {
        Iterator<CartItem> iterator = cart.getCartItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            item.setCart(null);   // break the child relationship
            iterator.remove();
        }
        return cartRepository.save(cart);

    }

    @Override
    public CartItem gatCartItem(Long itemId) {
        return cartItemRepository.findById(itemId).orElse(null);
    }
}
