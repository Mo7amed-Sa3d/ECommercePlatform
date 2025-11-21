package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.entity.Shipment;

import java.util.List;
import java.util.Optional;

public interface ShipmentService {
    Shipment createShipment(Long orderId, Shipment shipment);
    Shipment updateShipmentStatus(Long shipmentId, String status);
    Optional<Shipment> getShipmentById(Long shipmentId);
    List<Shipment> getShipmentsByOrder(Long orderId);
}
