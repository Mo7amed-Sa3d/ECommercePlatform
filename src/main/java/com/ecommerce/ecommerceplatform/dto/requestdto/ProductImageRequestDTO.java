package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductImageRequestDTO {
    private Long id;
    private String url;
    private String altText;
    private Integer displayOrder;
}
