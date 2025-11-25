package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter

public class OrderRequestDTO {
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private Instant createdAt;
    private List<OrderItemRequestDTO> orderItemResponseDTOList;
    private Long addressId;
}
