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
import com.ecommerce.ecommerceplatform.utility.UserUtility;

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
    private final UserUtility userUtility;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImplementation(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            UserUtility userUtility
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userUtility = userUtility;
    }

    // --------------------------------------------------------
    // Main Methods
    // --------------------------------------------------------

    @Override
    @Transactional
    public CartResponseDTO addItemToCartByUserID(Long productId, int quantity) {

        //TODO: the returned cart is null when created but works fine when get
        User user = userUtility.getCurrentUser();
        Cart cart = initializeUserCartIfNeeded(user);

        cart.setUpdatedAt(Instant.now());

        CartItem existingItem = findCartItemByProductId(cart, productId);

        if (existingItem != null) {
            updateExistingCartItem(existingItem, quantity);
        } else {
            addNewItemToCart(cart, productId, quantity);
        }

        return CartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO RemoveItemFromCart(Long itemId) {
        User user = userUtility.getCurrentUser();
        Cart cart = getUserCartFromDatabase(user);

        validateCartNotEmpty(cart);

        CartItem cartItem = cartItemRepository.findById(itemId).get();
        cart.removeCartItem(cartItem);

        return CartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartResponseDTO clearCart(Cart cart) {
        removeAllItemsFromCart(cart);
        return CartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartItemResponseDTO gatCartItem(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId).orElse(null);
        return CartItemMapper.toDTO(item);
    }

    @Override
    public CartResponseDTO getUserCart() {
        User user = userUtility.getCurrentUser();
        return CartMapper.toDTO(user.getCart());
    }


    // --------------------------------------------------------
    // Helper Methods
    // --------------------------------------------------------

    private Cart initializeUserCartIfNeeded(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setUser(user);
        }
        return cart;
    }

    private Cart findUserCart(User user) {
        return userRepository.findById(user.getId()).get().getCart();
    }

    private Cart getUserCartFromDatabase(User user) {
        return findUserCart(user);
    }

    private CartItem findCartItemByProductId(Cart cart, Long productId) {
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    private void updateExistingCartItem(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        if (cartItem.getQuantity() <= 0) {
            RemoveItemFromCart(cartItem.getId());
        }
    }

    private void addNewItemToCart(Cart cart, Long productId, int quantity) {
        CartItem newItem = new CartItem();
        newItem.setProduct(productRepository.findById(productId).get());
        newItem.setQuantity(quantity);
        cart.addCartItem(newItem);
    }

    private void validateCartNotEmpty(Cart cart) {
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
    }

    private void removeAllItemsFromCart(Cart cart) {
        Iterator<CartItem> iterator = cart.getCartItems().iterator();

        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            item.setCart(null);
            iterator.remove();
        }
    }
}
