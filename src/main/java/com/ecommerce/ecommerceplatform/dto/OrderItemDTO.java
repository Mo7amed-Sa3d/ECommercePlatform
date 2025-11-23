package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter

public class OrderItemDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal taxAmount;

}
