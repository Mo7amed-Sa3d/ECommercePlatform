package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class CartItemRequestDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;
}
