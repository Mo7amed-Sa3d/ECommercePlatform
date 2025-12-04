package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long amount; // Amount in cents (e.g., $10.00 = 1000)
    private Long orderId;
    private String currency; // "usd", "eur", etc.

}
