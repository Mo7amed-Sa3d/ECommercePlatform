package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class
PaymentDTO {
    private Long id;
    private String provider;
    private String providerTxnId;
    private String status;
    private BigDecimal amount;
    private Instant paidAt;
    private Long orderId;
}
