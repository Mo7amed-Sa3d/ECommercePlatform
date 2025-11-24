package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Instant createdAt;
    private Instant lastLogin;
    private String role;
}
