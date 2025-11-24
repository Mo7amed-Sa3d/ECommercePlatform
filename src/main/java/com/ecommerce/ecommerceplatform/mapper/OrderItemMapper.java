package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.OrderItemResponseDTO;
import com.ecommerce.ecommerceplatform.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemResponseDTO orderItemResponseDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemResponseDTO.getId());
        orderItem.setQuantity(orderItemResponseDTO.getQuantity());
        orderItem.setUnitPrice(orderItemResponseDTO.getUnitPrice());
        orderItem.setTaxAmount(orderItemResponseDTO.getTaxAmount());
        return orderItem;
    }

    public static OrderItemResponseDTO toDTO(OrderItem orderItem) {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setId(orderItem.getId());
        orderItemResponseDTO.setQuantity(orderItem.getQuantity());
        orderItemResponseDTO.setUnitPrice(orderItem.getUnitPrice());
        orderItemResponseDTO.setTaxAmount(orderItem.getTaxAmount());
        return orderItemResponseDTO;
    }

    public static List<OrderItemResponseDTO> toDtoList(List<OrderItem> orderItems) {
        List<OrderItemResponseDTO> orderItemResponseDTOList = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemResponseDTOList.add(toDTO(orderItem));
        }
        return orderItemResponseDTOList;
    }
}
