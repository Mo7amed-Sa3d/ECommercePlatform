package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Long userId, Long productId, Review review);
    void deleteReview(Long reviewId);
    List<Review> getReviewsByProduct(Long productId);
    List<Review> getReviewsByUser(Long userId);
}
