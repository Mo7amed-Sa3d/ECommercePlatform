package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserServices userServices, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userServices.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

}
