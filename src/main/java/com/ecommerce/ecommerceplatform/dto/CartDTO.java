package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter

public class CartDTO {
    private Long id;
    private Instant updatedAt;
    private Long userID;
    private List<CartItemDTO> cartItemDTOList;
}
