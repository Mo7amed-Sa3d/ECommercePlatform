package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.AddressRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.PaymentRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.dto.mapper.AddressMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.service.payment.PaymentService;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.param.AccountLinkCreateParams;
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
    private final UserUtility userUtility;
    private final PaymentService paymentService;

    @Autowired
    public UserController(UserServices userServices, UserUtility userUtility, PaymentService paymentService) {
        this.userServices = userServices;
        this.userUtility = userUtility;
        this.paymentService = paymentService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userServices.getUserByEmail(email);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponseDTO> addAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        User user = userUtility.getCurrentUser();
        Address savedAddress = userServices.addAddressToUser(user.getId(), AddressMapper.toEntity(addressRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressMapper.toDto(savedAddress));
    }



    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAddresses() {
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(AddressMapper.toDtoList(userServices.getAddresses(user.getId())));
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        User user = userUtility.getCurrentUser();
        userServices.deleteAddressFromUser(user.getId(),addressId);
        return ResponseEntity.ok().body("Done Deleting Address with id " + addressId + "From User" + user.getId());
    }

    @GetMapping("/{sellerId}/onboarding-link")
    public ResponseEntity<?> onboardingLink(@PathVariable Long sellerId) throws StripeException {
        Seller seller = userServices.getUserByID(sellerId).getSeller();
        return ResponseEntity.ok(paymentService.generateOnboardingLink(seller.getPaymentAccountId()));
    }

}