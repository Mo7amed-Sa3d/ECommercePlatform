package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class SellerResponseDTO {
    private Long id;
    private String sellerName;
    private boolean verified;
    private Instant createdAt;
}
