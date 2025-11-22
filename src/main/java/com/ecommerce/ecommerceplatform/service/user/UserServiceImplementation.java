package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserServices {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder
                                     ,AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole().equals("ROLE_SELLER")){
            Seller seller = user.getSeller();
            System.out.println(seller);
            user.setSeller(seller);
            System.out.println("seller saved");
        }
        return userRepository.save(user);
    }
    @Override
    public Address addAddressToUser(Long userId,Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.addAddress(address);
        userRepository.save(user);
        return address;
    }

    @Override
    public List<Address> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void deleteAddressFromUser(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address Not Found"));
        user.removeAddress(address);
    }
}
