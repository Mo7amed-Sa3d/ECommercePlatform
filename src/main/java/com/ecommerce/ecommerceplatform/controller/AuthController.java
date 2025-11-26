package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.AuthRequestDto;
import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AuthResponseDto;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.security.jwt.BlacklistService;
import com.ecommerce.ecommerceplatform.security.jwt.JwtService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    private final UserUtility userUtility;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BlacklistService blacklistService;

    @Autowired
    public AuthController(UserServices userServices,
                          UserUtility userUtility,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService, BlacklistService blacklistService) {
        this.userServices = userServices;
        this.userUtility = userUtility;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.blacklistService = blacklistService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
         );
         String token = jwtService.generateToken(authRequestDto.getEmail());
         return  ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove Bearer

        blacklistService.blackListToken(token);

        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userData) throws AccessDeniedException {
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerUser(userData)));
    }

    @PostMapping("/registerSeller")
    public ResponseEntity<UserResponseDTO> registerSeller(@RequestBody SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException {
        User admin = userUtility.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerSeller(admin,sellerRequestDTO)));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody UserRequestDTO userData) throws AccessDeniedException {
        User admin = userUtility.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDto(userServices.registerAdmin(admin,userData)));
    }
}
