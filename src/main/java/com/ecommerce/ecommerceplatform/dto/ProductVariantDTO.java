package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductVariantDTO {
    private Long id;
    private String sku;
    private Map<String,Object> attributes;
    private BigDecimal price;
    private Integer weightGrams;
    private Integer stockSize;

}
