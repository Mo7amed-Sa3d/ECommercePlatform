package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    User registerUser(User user);
    Optional<User> getUserByEmail(String email);
    Address addAddressToUser(Long userId,Address address);
    List<Address> getAddresses(Long userId);
    void deleteAddressFromUser(Long userId,Long addressId);
}
