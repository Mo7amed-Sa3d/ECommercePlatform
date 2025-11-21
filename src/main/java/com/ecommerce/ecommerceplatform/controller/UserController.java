package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userServices.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("{userId}/addresses")
    public ResponseEntity<?> addAddress(@PathVariable Long userId, @RequestBody Address address) {
        Address savedAddress = userServices.addAddressToUser(userId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @GetMapping("{userId}/addresses")
    public ResponseEntity<?> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(userServices.getAddresses(userId));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId){
        userServices.deleteAddressFromUser(userId,addressId);
        return ResponseEntity.ok().body("Done Deleting Address with id " + addressId + "From User" + userId);
    }
}