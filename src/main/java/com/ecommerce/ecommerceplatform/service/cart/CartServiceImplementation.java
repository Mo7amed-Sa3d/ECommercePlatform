package com.ecommerce.ecommerceplatform.service.cart;

import com.ecommerce.ecommerceplatform.dto.mapper.CartItemMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.CartMapper;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartItemResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.CartItem;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.CartItemRepository;
import com.ecommerce.ecommerceplatform.repository.CartRepository;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
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

@Service
public class CartServiceImplementation implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    CartRepository cartRepository;
    ProductService productService;
    UserServices userServices;
    CartItemRepository cartItemRepository;
    @Autowired
    public CartServiceImplementation(CartRepository cartRepository,
                                     UserServices userServices,
                                     ProductService productService,
                                     CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userServices = userServices;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public CartResponseDTO addItemToCartByUserID(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId).get();
        Cart cart = user.getCart();
        if(cart == null) {
            //TODO: Make this throw exception
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setUser(user);
        }
        cart.setUpdatedAt(Instant.now());
        boolean itemExists = false;
        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().getId().equals(productId)) {
                itemExists = true;
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                if(cartItem.getQuantity() <= 0) {
                    RemoveItemFromCart(userId,cartItem.getId());
                }
                break;
            }
        }
        if(!itemExists) {
            CartItem item = new CartItem();
            item.setProduct(productRepository.findById(productId).get());
            item.setQuantity(quantity);
            cart.addCartItem(item);
        }
        return CartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO RemoveItemFromCart(Long userId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId).get();
        Cart cart = userRepository.findById(userId).get().getCart();
        if(cart == null || cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        cart.removeCartItem(cartItem);
        return CartMapper.toDTO(cartRepository.save(cart));
    }


    //TODO: Delete the cart_item from the database not only breaking the link

    @Override
    @Transactional
    public CartResponseDTO clearCart(Cart cart) {
        Iterator<CartItem> iterator = cart.getCartItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            item.setCart(null);   // break the child relationship
            iterator.remove();
        }
        return CartMapper.toDTO(cartRepository.save(cart));

    }

    @Override
    public CartItemResponseDTO gatCartItem(Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElse(null);
        return CartItemMapper.toDTO(cartItem);
    }
}
