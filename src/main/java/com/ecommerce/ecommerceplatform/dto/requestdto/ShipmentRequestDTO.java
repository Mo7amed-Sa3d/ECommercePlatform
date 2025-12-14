package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class ShipmentRequestDTO {
    private Long addressId;
    private Long weightGrams;
    private Long orderId;
}
