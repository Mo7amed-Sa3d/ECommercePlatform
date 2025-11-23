package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.UserDTO;
import com.ecommerce.ecommerceplatform.entity.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
