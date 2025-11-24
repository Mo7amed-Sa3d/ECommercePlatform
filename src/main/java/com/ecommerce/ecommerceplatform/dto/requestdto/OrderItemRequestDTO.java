package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter

public class OrderItemRequestDTO {
    private Long userId;
    private Long orderId;
    private Integer quantity;
    private Long productId;
    private BigDecimal unitPrice;
    private BigDecimal taxAmount;

}
