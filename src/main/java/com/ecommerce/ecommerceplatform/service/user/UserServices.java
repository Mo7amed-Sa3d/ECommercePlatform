package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.Review;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    User registerUser(User user);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();

    // Address operations
    Address addAddress(Long userId, Address address);
    void removeAddress(Long userId, Long addressId);
    List<Address> getUserAddresses(Long userId);

    // Cart operations
    Cart getUserCart(Long userId);

    // Reviews
    Review addReview(Long userId, Long productId, Review review);
    void removeReview(Long reviewId);
    List<Review> getUserReviews(Long userId);

}
