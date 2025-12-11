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
import com.ecommerce.ecommerceplatform.repository.UserRepository;
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
    private final PaymentService paymentService;

    @Autowired
    public UserController(UserServices userServices, PaymentService paymentService) {
        this.userServices = userServices;
        this.paymentService = paymentService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponseDTO> addAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        var savedAddress = userServices.addAddressToUser(AddressMapper.toEntity(addressRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }



    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAddresses() {
        return ResponseEntity.ok(userServices.getAddresses());
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        userServices.deleteAddressFromUser(addressId);
        return ResponseEntity.ok().body("Done Deleting Address with id " + addressId);
    }

    @GetMapping("/{sellerId}/onboarding-link")
    public ResponseEntity<?> onboardingLink(@PathVariable Long sellerId) throws StripeException {
        return ResponseEntity.ok(paymentService.generateOnboardingLink());
    }

}