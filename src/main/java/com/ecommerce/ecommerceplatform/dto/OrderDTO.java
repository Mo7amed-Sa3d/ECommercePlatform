package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDTO {
    private Long id;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private Instant createdAt;
    private List<OrderItemDTO> orderItemDTOList;
}
