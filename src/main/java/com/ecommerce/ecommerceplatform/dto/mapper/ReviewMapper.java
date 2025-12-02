package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ReviewResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Review;

public class ReviewMapper {

    public static ReviewResponseDTO toDTO(Review review) {
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setBody(review.getBody());
        reviewResponseDTO.setTitle(review.getTitle());
        reviewResponseDTO.setCreatedAt(review.getCreatedAt());
        reviewResponseDTO.setRating(review.getRating());
        return reviewResponseDTO;

    }
}
