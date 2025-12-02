package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;

import java.time.Instant;

public class UserMapper {

    public static UserResponseDTO toDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setLastLogin(user.getLastLogin());
        userResponseDTO.setRole(user.getRole());
        return userResponseDTO;
    }

    public static User toEntity(UserRequestDTO userData) {
        User user = new User();
        user.setEmail(userData.getEmail());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setPhone(userData.getPhone());
        user.setRole(userData.getRole());
        user.setCreatedAt(Instant.now());
        user.setPassword(userData.getPassword());
        return user;
    }
}
