package com.ecommerce.ecommerceplatform.dto.responsedto;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.mapper.OrderItemMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private Long orderId;
    private List<OrderItemResponseDTO> orderItemList;
    private BigDecimal finalTotal;
    private Long shipmentId;
    public OrderSummaryDTO(Order order){
        orderId = order.getId();
        finalTotal = order.getTotalAmount();
        orderItemList = OrderItemMapper.toDtoList(order.getOrderItems());
        shipmentId = order.getShipment().getId();
    }
}
