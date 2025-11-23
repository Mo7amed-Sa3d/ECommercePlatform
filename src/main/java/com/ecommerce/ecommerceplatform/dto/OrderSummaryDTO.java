package com.ecommerce.ecommerceplatform.dto;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;
import com.ecommerce.ecommerceplatform.mapper.OrderItemMapper;
import com.ecommerce.ecommerceplatform.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private Long orderId;
    private List<OrderItemDTO> orderItemList;
    private BigDecimal finalTotal;
    public OrderSummaryDTO(Order order){
        orderId = order.getId();
        finalTotal = order.getTotalAmount();
        orderItemList = OrderItemMapper.toDtoList(order.getOrderItems());
    }
}
