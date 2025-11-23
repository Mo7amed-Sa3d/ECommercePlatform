package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;
}
