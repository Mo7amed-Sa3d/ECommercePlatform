package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class ShipmentResponseDTO {
    private String trackingNumber;
    public ShipmentResponseDTO(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
