package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class BrandRequestDTO {
    private String name;
    private String description;
    private String country;
    private Instant createdAt;

}
