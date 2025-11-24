package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class SellerRequestDTO {
    private Long id;
    private String sellerName;
    private boolean verified;
    private Instant createdAt;
}
