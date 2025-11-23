package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SellerDTO {
    private Long id;
    private String sellerName;
    private boolean verified;
    private Instant createdAt;
    private Long userId;
}
