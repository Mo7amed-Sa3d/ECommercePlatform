package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class ShipmentRequestDTO {
    private String carrier;
    private String trackingNumber;
    private String status;
    private Instant shoppedAt;
    private Instant deliveryAt;
    private Long orderId;
    private Long addressId;
}
