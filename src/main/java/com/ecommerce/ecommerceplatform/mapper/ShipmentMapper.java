package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.ShipmentDTO;
import com.ecommerce.ecommerceplatform.entity.Shipment;

public class ShipmentMapper {
    public static ShipmentDTO toDto(Shipment shipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setId(shipment.getId());
        shipmentDTO.setCarrier(shipment.getCarrier());
        shipmentDTO.setTrackingNumber(shipment.getTrackingNumber());
        shipmentDTO.setStatus(shipment.getStatus());
        shipmentDTO.setShoppedAt(shipment.getShippedAt());
        shipmentDTO.setDeliveryAt(shipment.getDeliveredAt());
        return shipmentDTO;

    }
}
