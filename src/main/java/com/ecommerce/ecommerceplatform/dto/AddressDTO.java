package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class AddressDTO {
    private Long id;
    private String fullName;
    private String line1;
    private String line2;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
}
