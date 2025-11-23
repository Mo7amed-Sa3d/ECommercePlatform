package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.ReviewDTO;
import com.ecommerce.ecommerceplatform.entity.Review;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setBody(review.getBody());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setRating(review.getRating());
        return reviewDTO;

    }
}
