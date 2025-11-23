package com.ecommerce.ecommerceplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter

public class ReviewDTO {
    private Long id;
    private Integer rating;
    private String title;
    private String body;
    private Instant createdAt;
}
