package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class ReviewRequestDTO {
    private Long id;
    private Integer rating;
    private String title;
    private String body;
    private Instant createdAt;
}
