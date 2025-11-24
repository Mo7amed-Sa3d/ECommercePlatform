package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;

    @Autowired
    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userData) {
        User newUser = UserMapper.toEntity(userData);
        User savedUser = userServices.registerUser(newUser);
        return ResponseEntity.ok(UserMapper.toDto(savedUser));
    }
    @PostMapping("/registerSeller")
    public ResponseEntity<UserResponseDTO> registerSeller(@RequestBody SellerRequestDTO userData) {
        User newUser = SellerMapper.toEntity(userData);
        newUser = userServices.registerUser(newUser);
        return ResponseEntity.ok(UserMapper.toDto(newUser));
    }

}
