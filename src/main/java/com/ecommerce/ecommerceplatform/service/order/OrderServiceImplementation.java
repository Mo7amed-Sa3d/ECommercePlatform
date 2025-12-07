package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {
    private final CartService cartService;
    private final AddressRepository addressRepository;
    UserServices userServices;
    OrderRepository orderRepository;
    public OrderServiceImplementation(UserServices userServices, OrderRepository orderRepository, CartService cartService, AddressRepository addressRepository) {
        this.userServices = userServices;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Order createOrder(User user) {
        Cart cart = user.getCart();
        if(cart == null || cart.getCartItems().isEmpty())
            throw new IllegalStateException("Cart is Empty");
        Order order = new Order();

        order.setCurrency("USD");
        order.setCreatedAt(Instant.now());
        order.setStatus("Pending Payment");
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
    public OrderSummaryDTO checkout(Long userId,Long addressId) {
        User user = userServices.getUserByID(userId);
        Order order = createOrder(user);
        createShipment(addressId, order);
        return new OrderSummaryDTO(order);
    }

    @Override
    public List<Order> getAllOrdersById(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Shipment createShipment(Long addressId,Order order) {
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        Optional<Address> optional = addressRepository.findById(addressId);
        if(optional.isEmpty())
            throw new IllegalStateException("Address Not Found");
        Address address = optional.get();
        shipment.setAddress(address);
        order.setShipment(shipment);
        shipment.setTrackingNumber("123456");
        shipment.setCarrier("Ahmed");
        return shipment;
    }

    @Override
    public Order findById(Long orderId) {
        var order = orderRepository.findById(orderId);
        if(order.isEmpty())
            throw new IllegalStateException("Order Not Found");
        return order.get();
    }

    @Override
    @Transactional
    public void markOrderPaid(Long id,String paymentId) {
        var optional = orderRepository.findById(id);
        if(optional.isEmpty())
            throw new IllegalStateException("Order Not Found");
        Order order = optional.get();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaidAt(Instant.now());
        payment.setPaymentId(paymentId);
        payment.setAmount(order.getTotalAmount());
        order.setStatus("Paid");
        order.setPayment(payment);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        var optional = orderRepository.findById(orderId);
        if(optional.isEmpty())
            throw new IllegalStateException("Order Not Found");

        return optional.get();
    }

}
