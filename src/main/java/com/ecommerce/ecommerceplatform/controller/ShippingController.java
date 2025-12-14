package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.ShipmentRequestDTO;
import com.ecommerce.ecommerceplatform.service.shipping.ShipmentService;
import com.ecommerce.ecommerceplatform.utility.FedexTokenManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/shipment")
public class ShippingController {

    private final ShipmentService shipmentService;

    public ShippingController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<?> createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO) throws AccessDeniedException {
        return ResponseEntity.ok(shipmentService.createShipment(shipmentRequestDTO));
    }

    @GetMapping
    public ResponseEntity<?> getShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO) {
        return ResponseEntity.ok(shipmentService.getShipment(shipmentRequestDTO));
    }

}
