package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    private final CartService cartService;
    UserServices userServices;
    OrderRepository orderRepository;
    public OrderServiceImplementation(UserServices userServices, OrderRepository orderRepository, CartService cartService) {
        this.userServices = userServices;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public Order createOrder(User user) {
        Cart cart = user.getCart();
        if(cart == null || cart.getCartItems().isEmpty())
            throw new IllegalStateException("Cart is Empty");
        Order order = new Order();

        order.setCurrency("EGP");
        //Map cart items to order items
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setUnitPrice(cartItem.getProduct().getBasePrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTaxAmount(BigDecimal.ZERO);
            order.addOrderItem(orderItem);
        }
        order.setTotalAmount(order.getTotalAmount());
        user.addOrder(order);

        var savedOrder = orderRepository.save(order);
        cartService.clearCart(cart);
        return savedOrder;
    }


    @Override
    @Transactional
    public OrderSummaryDTO checkout(Long userId) {
        User user = userServices.getUserByID(userId);
        Order order = createOrder(user);
        return new OrderSummaryDTO(order);
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findAll();
    }

}
