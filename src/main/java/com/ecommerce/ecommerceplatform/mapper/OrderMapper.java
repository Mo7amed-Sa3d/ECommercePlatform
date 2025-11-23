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

    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setCurrency(order.getCurrency());
        orderDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDTOList.add(OrderItemMapper.toDTO(orderItem));
        }
        orderDTO.setOrderItemDTOList(orderItemDTOList);

        return orderDTO;
    }

    public static List<OrderDTO> toDtoList(List<Order> orders) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order : orders) {
            orderDTOList.add(OrderMapper.toDTO(order));
        }
        return orderDTOList;
    }
}
