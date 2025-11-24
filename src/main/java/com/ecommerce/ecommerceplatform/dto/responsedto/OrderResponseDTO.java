package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter

public class OrderResponseDTO {
    private Long id;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private Instant createdAt;
    private List<OrderItemResponseDTO> orderItemResponseDTOList;
}
