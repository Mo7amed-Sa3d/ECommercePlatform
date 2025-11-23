package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.UserDTO;
import com.ecommerce.ecommerceplatform.entity.User;
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
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        User savedUser = userServices.registerUser(user);
        return ResponseEntity.ok(UserMapper.toDto(savedUser));
    }

}
