package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class BrandResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String country;
    private Instant createdAt;
}
