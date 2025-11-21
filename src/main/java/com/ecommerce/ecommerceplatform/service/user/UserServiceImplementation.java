package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.Review;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO: remove abstract
@Service
public class UserServiceImplementation implements UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public Address addAddress(Long userId, Address address) {
        return null;
    }

    @Override
    public void removeAddress(Long userId, Long addressId) {

    }

    @Override
    public List<Address> getUserAddresses(Long userId) {
        return List.of();
    }

    @Override
    public Cart getUserCart(Long userId) {
        return null;
    }

    @Override
    public Review addReview(Long userId, Long productId, Review review) {
        return null;
    }

    @Override
    public void removeReview(Long reviewId) {

    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        return List.of();
    }

}
