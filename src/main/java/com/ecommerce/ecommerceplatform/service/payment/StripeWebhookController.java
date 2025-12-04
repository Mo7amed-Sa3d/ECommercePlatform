package com.ecommerce.ecommerceplatform.service.payment;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/stripe-webhook")
    public ResponseEntity<String> handleStripeEvent(HttpServletRequest request, @RequestBody String payload) {
        String sigHeader = request.getHeader("Stripe-Signature");

        try {
            Event event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret
            );

            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject().orElse(null);

                System.err.println("Stripe Webhook Success");
                // Mark order as paid in DB
//                orderService.markOrderPaid(intent.getId());
            }

            return ResponseEntity.ok("Payment Intent: " + event.getType());
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("");
        }
    }

}
//stripe listen --forward-to localhost:8080/api/stripe-webhook