package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.ReviewRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ReviewResponseDTO;
import com.ecommerce.ecommerceplatform.service.product.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/review")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    @GetMapping()
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        return ResponseEntity.ok(reviewService.createReview(reviewRequestDTO));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@RequestBody ReviewRequestDTO reviewRequestDTO,@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.updateReview(reviewRequestDTO,reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review with id " + reviewId + " has been deleted");
    }
}
