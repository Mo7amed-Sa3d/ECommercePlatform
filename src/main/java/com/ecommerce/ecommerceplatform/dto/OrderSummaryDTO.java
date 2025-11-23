package com.ecommerce.ecommerceplatform.dto;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
    private Long orderId;
    List<OrderItem> orderItemList;
    private BigDecimal finalTotal;
    public OrderSummaryDTO(Order order){
        orderId = order.getId();
        finalTotal = order.getTotalAmount();
        orderItemList = order.getOrderItems();
    }
}
