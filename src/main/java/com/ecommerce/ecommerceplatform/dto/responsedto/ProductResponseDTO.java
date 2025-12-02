package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String title;
    private String description;
    private BigDecimal basePrice;
    private Boolean active;
    private Map<String,Object> attributes;
    private Instant createdAt;
    List<ProductImageResponseDTO> images;

}
