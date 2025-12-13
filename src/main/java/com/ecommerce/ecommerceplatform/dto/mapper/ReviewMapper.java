package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ReviewResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Review;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ReviewResponseDTO> toDTOList(List<Review> all) {
        List<ReviewResponseDTO> reviewResponseDTOList = new ArrayList<>();
        for(Review review : all) {
            ReviewResponseDTO reviewResponseDTO = toDTO(review);
            reviewResponseDTOList.add(reviewResponseDTO);
        }
        return reviewResponseDTOList;
    }
}
