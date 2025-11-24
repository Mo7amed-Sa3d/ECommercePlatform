package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductRequestDTO {
    private String sku;
    private String title;
    private String description;
    private BigDecimal basePrice;
    private Boolean active;
    private Map<String,Object> attributes;
    private Instant createdAt;
    private Long brandId;
    private Long categoryId;
}
