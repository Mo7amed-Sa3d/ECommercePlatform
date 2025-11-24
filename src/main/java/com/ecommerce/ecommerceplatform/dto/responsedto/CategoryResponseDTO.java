package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Long parentId;
}
