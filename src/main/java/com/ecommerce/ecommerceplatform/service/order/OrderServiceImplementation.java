package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.OrderSummary;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    UserServices userServices;
    OrderRepository orderRepository;
    public OrderServiceImplementation(UserServices userServices,OrderRepository orderRepository) {
        this.userServices = userServices;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(User user) {
        Cart cart = user.getCart();
        if(cart == null)
            throw new IllegalStateException("Cart is Empty");
        Order order = new Order();

        //Map cart items to order items
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            order.addOrderItem(orderItem);
        }

        //process payment

        user.addOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public OrderSummary checkout(Long userId) {
        User user = userServices.getUserByID(userId);
        Order order = createOrder(user);
        return new OrderSummary(order);
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findAll();
    }

}
