package com.ecommerce.ecommerceplatform.controller.payment;

import com.ecommerce.ecommerceplatform.dto.requestdto.PaymentRequest;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.service.payment.PaymentService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService paymentService;
    private final UserUtility userUtility;
    private final OrderRepository orderRepository;
    public PaymentController(PaymentService paymentService, UserUtility userUtility, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.userUtility = userUtility;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest request) {
        try {
        Order order = orderRepository.findById(request.getOrderId()).get();
        if(order.getStatus().equals("Paid"))
            throw new IllegalArgumentException("Order has already been paid");

        var paymentIntent = paymentService.createPaymentIntent(order);
        return ResponseEntity.ok(Map.of(
                    "clientSecret", paymentIntent.getClientSecret(),
                    "orderId", request.getOrderId()
            ));
        } catch (Exception e) {
            System.err.println("payment controller error " + e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/acount-requirement")
    public ResponseEntity<List<String>> accountRequirement() throws StripeException {
        return ResponseEntity.ok(paymentService.getAccountRequirements());
    }

    @GetMapping("/onboarding-link")
    public ResponseEntity<?> onboardingLink() throws StripeException {
        return ResponseEntity.ok(paymentService.generateOnboardingLink());
    }

}
