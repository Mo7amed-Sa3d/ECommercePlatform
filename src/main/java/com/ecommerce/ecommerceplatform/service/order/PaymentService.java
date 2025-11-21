package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment processPayment(Long orderId, Payment payment);
    Payment getPaymentById(Long paymentId);
    List<Payment> getPaymentsByUser(Long userId);
}
