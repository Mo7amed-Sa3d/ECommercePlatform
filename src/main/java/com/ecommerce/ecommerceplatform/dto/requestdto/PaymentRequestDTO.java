package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class
PaymentRequestDTO {
    private Long id;
    private String provider;
    private String providerTxnId;
    private String status;
    private BigDecimal amount;
    private Instant paidAt;
}
