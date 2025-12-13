package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.ReviewRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
    public ReviewResponseDTO updateReview(ReviewRequestDTO reviewRequestDTO,Long reviewId);
    public void deleteReview(Long reviewId);
    public ReviewResponseDTO getReview(Long reviewId);

    List<ReviewResponseDTO> getAllReviews();
}
