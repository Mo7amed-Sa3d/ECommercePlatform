package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class CartItemResponseDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;
}
