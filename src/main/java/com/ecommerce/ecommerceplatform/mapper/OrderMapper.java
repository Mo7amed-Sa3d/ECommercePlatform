package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.OrderDTO;
import com.ecommerce.ecommerceplatform.dto.OrderItemDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order toEntity(OrderDTO orderDTO,List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setCurrency(orderDTO.getCurrency());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setOrderItems(orderItemList);
        return order;
    }
}
