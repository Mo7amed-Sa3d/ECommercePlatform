package com.ecommerce.ecommerceplatform.service.payment;

import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final OrderService orderService;

    public StripeWebhookController(OrderService orderService) {
        this.orderService = orderService;
    }

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

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(event.toJson());  // get event JSON
                JsonNode metadataNode = root.path("data").path("object").path("metadata");
                String orderIdStr = metadataNode.path("orderId").asText(null);  // null if missing
                String paymentId = root.path("data").path("object").path("id").asText(null);

                if(orderIdStr == null)
                    throw new ObjectNotFoundException(event,"Payment Intent Object is null");

                System.err.println("Stripe Webhook Success " + orderIdStr);
                System.err.println("Stripe Webhook Payload " + paymentId);
                orderService.markOrderPaid(Long.valueOf(orderIdStr), paymentId);
            }

            return ResponseEntity.ok("Payment Intent: " + event.getType());
        } catch (SignatureVerificationException | JsonProcessingException e) {
            return ResponseEntity.status(400).body("");
        }
    }

}
//stripe listen --forward-to localhost:8080/api/stripe-webhook