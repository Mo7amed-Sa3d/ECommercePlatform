package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class ProductImageResponseDTO {
    private Long id;
    private String url;
    private String altText;
    private Integer displayOrder;
}
