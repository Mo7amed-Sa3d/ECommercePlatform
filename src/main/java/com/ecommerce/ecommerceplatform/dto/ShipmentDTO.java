package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipmentDTO {
    private Long id;
    private String carrier;
    private String trackingNumber;
    private String status;
    private Instant shoppedAt;
    private Instant deliveryAt;
    private Long orderId;

}
