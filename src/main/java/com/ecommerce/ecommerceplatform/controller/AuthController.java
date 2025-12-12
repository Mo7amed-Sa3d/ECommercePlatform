package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.AuthRequestDto;
import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AuthResponseDto;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import com.ecommerce.ecommerceplatform.security.jwt.JwtService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserUtility userUtility;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserServices userServices,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager, UserUtility userUtility, UserRepository userRepository) {
        this.userServices = userServices;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userUtility = userUtility;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
            );
        String token = jwtService.generateToken(authRequestDto.getEmail());
        User user = userRepository.findByEmail(authRequestDto.getEmail()).get();
        user.setLastLogin(Instant.now());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        jwtService.logout(authHeader);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userData) throws AccessDeniedException, InvalidAttributesException {
        return ResponseEntity.ok(userServices.registerUser(userData));
    }

    @PostMapping("/registerSeller")
    public ResponseEntity<UserResponseDTO> registerSeller(@RequestBody SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException, StripeException {
        return ResponseEntity.ok(userServices.registerSeller(sellerRequestDTO));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody UserRequestDTO userData) throws AccessDeniedException, InvalidAttributesException {
        return ResponseEntity.ok(userServices.registerAdmin(userData));
    }
}
