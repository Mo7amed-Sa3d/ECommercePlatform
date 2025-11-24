package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.OrderItemResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {



    public static OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setCurrency(order.getCurrency());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> orderItemResponseDTOList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemResponseDTOList.add(OrderItemMapper.toDTO(orderItem));
        }
        orderResponseDTO.setOrderItemResponseDTOList(orderItemResponseDTOList);

        return orderResponseDTO;
    }

    public static List<OrderResponseDTO> toDtoList(List<Order> orders) {
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for(Order order : orders) {
            orderResponseDTOList.add(OrderMapper.toDTO(order));
        }
        return orderResponseDTOList;
    }
}
