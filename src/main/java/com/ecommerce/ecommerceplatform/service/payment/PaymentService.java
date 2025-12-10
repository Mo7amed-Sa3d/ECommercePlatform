package com.ecommerce.ecommerceplatform.service.payment;

import com.ecommerce.ecommerceplatform.dto.requestdto.PaymentRequest;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;

import java.util.List;

public interface PaymentService {
    Account createSellerAccount() throws StripeException;
    String generateOnboardingLink(String accountId) throws StripeException;
    PaymentIntent createPaymentIntent(Order order) throws StripeException;
}
