package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.SellerResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    private final UserUtility userUtility;

    @Autowired
    public AuthController(UserServices userServices, UserUtility userUtility) {
        this.userServices = userServices;
        this.userUtility = userUtility;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userData) throws AccessDeniedException {
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerUser(userData)));
    }

    @PostMapping("/registerSeller")
    public ResponseEntity<UserResponseDTO> registerSeller(@RequestBody SellerRequestDTO sellerRequestDTO, Authentication authentication) throws AccessDeniedException, InvalidAttributesException {
        User admin = userUtility.getCurrentUser(authentication);
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerSeller(admin,sellerRequestDTO)));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody UserRequestDTO userData,Authentication authentication) throws AccessDeniedException {
        User admin = userUtility.getCurrentUser(authentication);
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerAdmin(admin,userData)));
    }
}
