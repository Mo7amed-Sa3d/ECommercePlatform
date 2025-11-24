package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class ShipmentResponseDTO {
    private Long id;
    private String carrier;
    private String trackingNumber;
    private String status;
    private Instant shoppedAt;
    private Instant deliveryAt;

}
