package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CartRepository;
import com.ecommerce.ecommerceplatform.service.product.ProductService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    CartRepository cartRepository;
    ProductService productService;
    UserServices userServices;
    @Autowired
    public CartServiceImplementation(CartRepository cartRepository,
                                     UserServices userServices,
                                     ProductService productService
                                     ) {
        this.cartRepository = cartRepository;
        this.userServices = userServices;
        this.productService = productService;
    }

    @Override
    public Cart addItemToCartByUserID(Long userId, Long productId, int quantity) {
        Cart cart = userServices.getCartByUserID(userId);
        if(cart == null) {
            cart = new Cart();
            User user = userServices.getUserByID(userId);
            cart.setUser(user);
        }
        CartItem item = new CartItem();
        item.setProduct(productService.getProductById(productId));
        item.setQuantity(quantity);
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
