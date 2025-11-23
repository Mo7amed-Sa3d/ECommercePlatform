package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductImageDTO {
    private Long id;
    private String url;
    private String altText;
    private Integer displayOrder;
    private Long productId;

}
