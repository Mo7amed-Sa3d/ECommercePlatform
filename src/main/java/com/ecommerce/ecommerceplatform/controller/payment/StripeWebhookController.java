package com.ecommerce.ecommerceplatform.controller.payment;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;
import com.ecommerce.ecommerceplatform.service.mailing.MailService;
import com.ecommerce.ecommerceplatform.service.order.OrderService;
import com.ecommerce.ecommerceplatform.service.seller.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final OrderService orderService;
    private final SellerService sellerService;
    private final MailService mailService;
    public StripeWebhookController(OrderService orderService, SellerService sellerService,MailService mailService) {
        this.orderService = orderService;
        this.sellerService = sellerService;
        this.mailService = mailService;
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
            EventDataObjectDeserializer des = event.getDataObjectDeserializer();
            var op = des.getObject();
            if(op.isEmpty())
                System.err.println("op is empty");
            if ("payment_intent.succeeded".equals(event.getType())) {
                System.err.println(event);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(event.toJson());  // get event JSON
                JsonNode metadataNode = root.path("data").path("object").path("metadata");
                String orderIdStr = metadataNode.path("orderId").asText(null);  // null if missing
                String paymentId = root.path("data").path("object").path("id").asText(null);

                if(orderIdStr == null)
                    throw new ObjectNotFoundException(event,"Payment Intent Object is null");

                Order order = orderService.getOrderById(Long.parseLong(orderIdStr));
                for(OrderItem orderItem : order.getOrderItems()) {
                    Long amount = orderItem.getQuantity() * orderItem.getUnitPrice().longValue();
                    orderItem.setDues(orderItem.getDues() + amount);
                }
                System.err.println("Stripe Webhook Success " + orderIdStr);
                System.err.println("Stripe Webhook Payload " + paymentId);
                orderService.markOrderPaid(Long.valueOf(orderIdStr), paymentId);
                mailService.sendEmail(order.getUser().getEmail(),"Payment Successful!",
                        "Payment successful for order " + order.getId());
            }
            return ResponseEntity.ok("Payment Intent: " + event.getType());
        } catch (JsonProcessingException | StripeException e) {
            System.err.println("Stripe Webhook Error " + e.getMessage());
            return ResponseEntity.status(400).body("");
        }
    }

}