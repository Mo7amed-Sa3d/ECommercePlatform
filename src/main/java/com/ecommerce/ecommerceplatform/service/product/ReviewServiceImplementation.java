package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.mapper.ReviewMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.ReviewRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ReviewResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Review;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import com.ecommerce.ecommerceplatform.repository.ReviewRepository;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserUtility userUtility;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductRepository productRepository, UserUtility userUtility) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userUtility = userUtility;
    }

    @Override
    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = new Review();
        User user = userUtility.getCurrentUser();

        user.addReview(review);

        review.setTitle(reviewRequestDTO.getTitle());
        review.setBody(reviewRequestDTO.getBody());
        review.setRating(reviewRequestDTO.getRating());
        review.setCreatedAt(Instant.now());

        Product product = getProductById(reviewRequestDTO.getProductId());

        product.addReview(review);
        return ReviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(ReviewRequestDTO reviewRequestDTO,Long reviewId) {
        Review review = verifyOwnershipAndGetReview(reviewId);

        review.setTitle(reviewRequestDTO.getTitle());
        review.setBody(reviewRequestDTO.getBody());
        review.setRating(reviewRequestDTO.getRating());
        review.setCreatedAt(Instant.now());

        return ReviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        Review review = verifyOwnershipAndGetReview(id);
        reviewRepository.delete(review);
    }

    @Override
    public ReviewResponseDTO getReview(Long reviewId) {
        return ReviewMapper.toDTO(verifyOwnershipAndGetReview(reviewId));
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return ReviewMapper.toDTOList(reviewRepository.findAll());
    }



    //Helper functions
    private Product getProductById(Long productId){
        var optional =  productRepository.findById(productId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        return optional.get();
    }

    private Review verifyOwnershipAndGetReview(Long reviewId) {
        User user = userUtility.getCurrentUser();
        var optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty())
            throw new EntityNotFoundException("Review not found");
        Review review = optionalReview.get();

        if(!review.getUser().getId().equals(user.getId()))
            throw new EntityNotFoundException("Review Not Found");

        return review;
    }
}
