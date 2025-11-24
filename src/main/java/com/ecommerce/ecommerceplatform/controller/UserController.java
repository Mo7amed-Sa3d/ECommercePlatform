package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.AddressRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.AddressMapper;
import com.ecommerce.ecommerceplatform.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userServices.getUserByEmail(email);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @PostMapping("{userId}/addresses")
    public ResponseEntity<AddressResponseDTO> addAddress(@PathVariable Long userId, @RequestBody AddressRequestDTO addressRequestDTO) {
        Address savedAddress = userServices.addAddressToUser(userId, AddressMapper.toEntity(addressRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressMapper.toDto(savedAddress));
    }

    @GetMapping("{userId}/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(AddressMapper.toDtoList(userServices.getAddresses(userId)));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId){
        userServices.deleteAddressFromUser(userId,addressId);
        return ResponseEntity.ok().body("Done Deleting Address with id " + addressId + "From User" + userId);
    }
}