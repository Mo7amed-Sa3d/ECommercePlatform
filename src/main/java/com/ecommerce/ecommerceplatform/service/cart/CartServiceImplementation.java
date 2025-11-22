package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CartRepository;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    CartRepository cartRepository;
    UserServices userServices;
    @Autowired
    public CartServiceImplementation(CartRepository cartRepository,UserServices userServices) {
        this.cartRepository = cartRepository;
        this.userServices = userServices;
    }

    @Override
    public Cart addItemToCartByUserID(Long userId, CartItem item) {
        Cart cart = userServices.getCartByUserID(userId);
        if(cart == null) {
            cart = new Cart();
            User user = userServices.getUserByID(userId);
            cart.setUser(user);
        }
        cart.addCartItem(item);
        return cartRepository.save(cart);
    }

    @Override
    public Cart RemoveItemFromCart(Long userId, CartItem item) {
        Cart cart = userServices.getCartByUserID(userId);
        cart.removeCartItem(item);
        return cartRepository.save(cart);
    }

}
