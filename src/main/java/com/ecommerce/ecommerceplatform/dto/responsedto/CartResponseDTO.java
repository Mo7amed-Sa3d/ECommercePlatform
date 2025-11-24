package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter

public class CartResponseDTO {
    private Long id;
    private Instant updatedAt;
    private Long userID;
    private List<CartItemResponseDTO> cartItemResponseDTOList;
}
