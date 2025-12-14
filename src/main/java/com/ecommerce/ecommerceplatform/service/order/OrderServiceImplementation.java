package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.configuration.cache.CacheNames;
import com.ecommerce.ecommerceplatform.dto.mapper.OrderMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.ShipmentMapper;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderSummaryDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.cart.CartService;
import com.ecommerce.ecommerceplatform.service.mailing.MailServiceImplementation;
import com.ecommerce.ecommerceplatform.utility.UserUtility;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final MailServiceImplementation mailService;
    private final UserUtility userUtility;

    public OrderServiceImplementation(
            OrderRepository orderRepository,
            CartService cartService,
            AddressRepository addressRepository,
            MailServiceImplementation mailService,
            UserUtility userUtility
    ) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.mailService = mailService;
        this.userUtility = userUtility;
    }

    // ================================================================
    // Order creation
    // ================================================================

    @Override
    @Transactional
    public Order createOrder(User user) {
        Cart cart = validateAndGetCart(user);

        Order order = initializeOrder();
        mapCartItemsToOrder(cart, order);

        order.setTotalAmount(order.getTotalAmount());
        user.addOrder(order);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart);

        return savedOrder;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNames.orderList, key = "@userUtility.getCurrentUser().id")
    })
    public OrderSummaryDTO checkout(Long addressId) {
        User user = userUtility.getCurrentUser();

        Order order = createOrder(user);

//        createShipment(addressId, order);

        sendOrderConfirmationEmail(user, order);

        return new OrderSummaryDTO(order);
    }


    // ================================================================
    // Read operations
    // ================================================================

    @Override
    @Cacheable(value = CacheNames.orders, key = "@userUtility.getCurrentUser().id")
    public List<OrderResponseDTO> getAllOrdersById() {
        User user = userUtility.getCurrentUser();
        return OrderMapper.toDtoList(orderRepository.findAllByUserId(user.getId()));
    }

    @Override
    @Cacheable(value = CacheNames.orders, key = "#orderId")
    public OrderResponseDTO findById(Long orderId) {
        Order order = fetchOrderOrThrow(orderId);
        return OrderMapper.toDTO(order);
    }



    // ================================================================
    // Shipment creation
    // ================================================================

//    @Override
//    @Transactional
//    public ShipmentResponseDTO createShipment(Long addressId, Order order) {
//        Address address = fetchAddressOrThrow(addressId);
//
//        Shipment shipment = new Shipment();
//        shipment.setOrder(order);
//        shipment.setAddress(address);
//        shipment.setTrackingNumber("123456");
//        shipment.setCarrier("Ahmed");
//        order.setShipment(shipment);
//
//        return ShipmentMapper.toDto(shipment);
//    }


    // ================================================================
    // Payment
    // ================================================================

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNames.orders, key = "#id"),
          //  @CacheEvict(value = CacheNames.orderList, key = "@userUtility.getCurrentUser().id")
    })
    public void markOrderPaid(Long id, String paymentId) {
        Order order = fetchOrderOrThrow(id);

        Payment payment = createPayment(order, paymentId);

        order.setStatus("Paid");
        order.setPayment(payment);

        orderRepository.save(order);
    }


    // ================================================================
    // Helper Methods
    // ================================================================

    private Cart validateAndGetCart(User user) {
        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is Empty");
        }
        return cart;
    }

    private Order initializeOrder() {
        Order order = new Order();
        order.setCurrency("USD");
        order.setCreatedAt(Instant.now());
        order.setStatus("Pending Payment");
        return order;
    }

    private void mapCartItemsToOrder(Cart cart, Order order) {
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setUnitPrice(cartItem.getProduct().getBasePrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTaxAmount(BigDecimal.ZERO);
            order.addOrderItem(orderItem);
        }
    }

    private void sendOrderConfirmationEmail(User user, Order order) {
        mailService.sendEmail(
                user.getEmail(),
                "Order Placed Successfully",
                "Your order has been placed successfully" + order.getOrderItems().toString()
        );
    }

    private Order fetchOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order Not Found"));
    }

    private Address fetchAddressOrThrow(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalStateException("Address Not Found"));
    }

    private Payment createPayment(Order order, String paymentId) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaidAt(Instant.now());
        payment.setPaymentId(paymentId);
        payment.setAmount(order.getTotalAmount());
        return payment;
    }
}
