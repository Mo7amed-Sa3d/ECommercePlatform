package com.ecommerce.ecommerceplatform.service.shipping;


import com.ecommerce.ecommerceplatform.dto.requestdto.ShipmentRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;
import java.util.Map;

public interface ShipmentService {
    ShipmentResponseDTO createShipment(ShipmentRequestDTO shipmentRequestDTO) throws AccessDeniedException;
    ShipmentResponseDTO getShipment(ShipmentRequestDTO shipmentRequestDTO);
}

