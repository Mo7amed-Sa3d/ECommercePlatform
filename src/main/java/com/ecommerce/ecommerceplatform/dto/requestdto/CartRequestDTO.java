package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter

public class CartRequestDTO {
    private Instant updatedAt;
    private Long userID;
    private List<CartItemRequestDTO> cartItemResponseDTOList;
}
