package com.ecommerce.ecommerceplatform.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class ProductDTO {
    private Long id;
    private String sku;
    private String title;
    private String description;
    private BigDecimal basePrice;
    private Boolean active;
    private Map<String,Object> attributes;
    private Instant createdAt;
    private Long sellerId;
    private Long brandId;
    
}
