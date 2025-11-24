package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductVariantResponseDTO {
    private Long id;
    private String sku;
    private Map<String,Object> attributes;
    private BigDecimal price;
    private Integer weightGrams;
    private Integer stockSize;

}
