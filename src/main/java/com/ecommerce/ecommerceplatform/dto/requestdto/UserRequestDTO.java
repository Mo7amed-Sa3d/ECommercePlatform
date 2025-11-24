package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;

}