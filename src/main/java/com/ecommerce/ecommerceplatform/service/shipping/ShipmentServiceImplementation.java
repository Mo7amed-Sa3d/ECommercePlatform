package com.ecommerce.ecommerceplatform.service.shipping;

import com.ecommerce.ecommerceplatform.client.FedexClient;
import com.ecommerce.ecommerceplatform.configuration.FedexConfig;
import com.ecommerce.ecommerceplatform.dto.mapper.ShipmentMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.ShipmentRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ShipmentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.Shipment;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.OrderRepository;
import com.ecommerce.ecommerceplatform.repository.ShipmentRepository;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class ShipmentServiceImplementation implements ShipmentService {

    private final FedexClient fedexClient;
    private final FedexConfig fedexConfig;
    private final AddressRepository addressRepository;
    private final UserUtility userUtility;
    private final OrderRepository orderRepository;
    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImplementation(FedexClient fedexClient,
                                         FedexConfig fedexConfig,
                                         AddressRepository addressRepository,
                                         UserUtility userUtility, OrderRepository orderRepository, ShipmentRepository shipmentRepository) {
        this.fedexClient = fedexClient;
        this.fedexConfig = fedexConfig;
        this.addressRepository = addressRepository;
        this.userUtility = userUtility;
        this.orderRepository = orderRepository;
        this.shipmentRepository = shipmentRepository;
    }


    @Override
    @Transactional
    public ShipmentResponseDTO createShipment(ShipmentRequestDTO shipmentRequestDTO) throws AccessDeniedException {

        User user = userUtility.getCurrentUser();
        if(!user.getRole().equals("ROLE_ADMIN") || !user.getRole().equals("ROLE_SELLER"))
            throw new AccessDeniedException("Access Denied!");

        Address address = getAddress(shipmentRequestDTO.getAddressId());

        Map<String, Object> payload = buildPayload(address);

        var response = fedexClient.createShipment(payload);

        System.out.println(response);

        Map<String, Object> output = (Map<String, Object>) response.get("output");

        List<Map<String, Object>> transactionShipments =
                (List<Map<String, Object>>) output.get("transactionShipments");

        Map<String, Object> firstShipment = transactionShipments.get(0);

        // Get master tracking number (main shipment tracking)
        String masterTrackingNumber =
                (String) firstShipment.get("masterTrackingNumber");

        Shipment shipment = new Shipment();
        shipment.setAddress(address);
        shipment.setTrackingNumber(masterTrackingNumber);

        Order order = orderRepository.findById(shipmentRequestDTO.getOrderId()).get();
        shipment.setOrder(order);

        shipmentRepository.save(shipment);

        return new  ShipmentResponseDTO(masterTrackingNumber);
    }

    @Override
    public ShipmentResponseDTO getShipment(ShipmentRequestDTO shipmentRequestDTO) {
        Order order = orderRepository.findById(shipmentRequestDTO.getOrderId()).get();
        return ShipmentMapper.toDto(order.getShipment());
    }


//Helper Functions

    private Address getAddress(Long addressId) {
        var address = addressRepository.findById(addressId);
        if(address.isEmpty())
            throw new EntityNotFoundException("Address not found");
        User user = userUtility.getCurrentUser();

        var userIdFromAddress = address.get().getUser().getId();

        if(!userIdFromAddress.equals(user.getId()))
            throw new EntityNotFoundException("Address not found");

        return address.get();
    }
    public Map<String, Object> buildPayload(Address address) {
        Map<String, Object> payload = new HashMap<>();

        // Account number (top level)
        payload.put("accountNumber", Map.of("value", "740561073"));
        payload.put("labelResponseOptions", "URL_ONLY");

        // Requested shipment
        Map<String, Object> requestedShipment = new HashMap<>();

        // Shipper
        Map<String, Object> shipperAddress = new HashMap<>();
        shipperAddress.put("streetLines", List.of("SENDER ADDRESS 1"));
        shipperAddress.put("city", "MEMPHIS");
        shipperAddress.put("stateOrProvinceCode", "TN");
        shipperAddress.put("postalCode", "38116");
        shipperAddress.put("countryCode", "US");

        requestedShipment.put("shipper", Map.of(
                "contact", Map.of(
                        "personName", "SENDER NAME",
                        "phoneNumber", "9018328595"
                ),
                "address", shipperAddress
        ));

        // Recipient
        Map<String, Object> recipientAddress = new HashMap<>();
        recipientAddress.put("streetLines", List.of("RECIPIENT ADDRESS 1"));
        recipientAddress.put("city", "MEMPHIS");
        recipientAddress.put("stateOrProvinceCode", "TN");
        recipientAddress.put("postalCode", "38116");
        recipientAddress.put("countryCode", "US");

        requestedShipment.put("recipients", List.of(
                Map.of(
                        "contact", Map.of(
                                "personName", "RECIPIENT NAME",
                                "phoneNumber", "9018328595"
                        ),
                        "address", recipientAddress
                )
        ));

        // Service details
        requestedShipment.put("serviceType", "STANDARD_OVERNIGHT");
        requestedShipment.put("packagingType", "YOUR_PACKAGING");
        requestedShipment.put("pickupType", "DROPOFF_AT_FEDEX_LOCATION");

        // Shipping charges payment
        Map<String, Object> accountNumberMap = new HashMap<>();
        accountNumberMap.put("value", "740561073");
        accountNumberMap.put("key", "258dd8851b5642048360ed3e2d9a2135");

        // Build payor address (same as shipper)
        Map<String, Object> payorAddress = new HashMap<>();
        payorAddress.put("streetLines", List.of("SENDER ADDRESS 1"));
        payorAddress.put("city", "MEMPHIS");
        payorAddress.put("stateOrProvinceCode", "TN");
        payorAddress.put("postalCode", "38116");
        payorAddress.put("countryCode", "US");

        requestedShipment.put("shippingChargesPayment", Map.of(
                "paymentType", "SENDER",
                "payor", Map.of(
                        "responsibleParty", Map.of(
                                "accountNumber", accountNumberMap
                        ),
                        "address", payorAddress
                )
        ));

        // Label specification
        requestedShipment.put("labelSpecification", Map.of(
                "labelFormatType", "COMMON2D"
        ));

        // Package line items
        requestedShipment.put("requestedPackageLineItems", List.of(
                Map.of(
                        "weight", Map.of(
                                "units", "LB",
                                "value", "20"
                        )
                )
        ));

        payload.put("requestedShipment", requestedShipment);

        return payload;
    }

}
