package com.ecommerce.ecommerceplatform.dto.responsedto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class ReviewResponseDTO {
    private Long id;
    private Integer rating;
    private String title;
    private String body;
    private Instant createdAt;
}
