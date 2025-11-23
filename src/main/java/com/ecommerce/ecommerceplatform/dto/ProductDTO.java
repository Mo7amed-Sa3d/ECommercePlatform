package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductDTO {
    private Long id;
    private String sku;
    private String title;
    private String description;
    private BigDecimal basePrice;
    private Boolean active;
    private Map<String,Object> attributes;
    private Instant createdAt;
}
