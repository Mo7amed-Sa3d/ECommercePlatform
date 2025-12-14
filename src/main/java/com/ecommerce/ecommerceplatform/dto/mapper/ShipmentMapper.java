package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Shipment;

public class ShipmentMapper {

    public static ShipmentResponseDTO toDto(Shipment shipment) {
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setTrackingNumber(shipment.getTrackingNumber());
        return shipmentResponseDTO;
    }
}
