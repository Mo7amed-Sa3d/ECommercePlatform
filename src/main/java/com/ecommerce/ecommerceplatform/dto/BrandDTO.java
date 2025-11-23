package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BrandDTO {
    private Long id;
    private String name;
    private String description;
    private String country;
    private Instant createdAt;
}
