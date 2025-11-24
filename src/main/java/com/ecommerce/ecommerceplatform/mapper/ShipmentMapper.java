package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Shipment;

public class ShipmentMapper {
    public static ShipmentResponseDTO toDto(Shipment shipment) {
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(shipment.getId());
        shipmentResponseDTO.setCarrier(shipment.getCarrier());
        shipmentResponseDTO.setTrackingNumber(shipment.getTrackingNumber());
        shipmentResponseDTO.setStatus(shipment.getStatus());
        shipmentResponseDTO.setShoppedAt(shipment.getShippedAt());
        shipmentResponseDTO.setDeliveryAt(shipment.getDeliveredAt());
        return shipmentResponseDTO;

    }
}
