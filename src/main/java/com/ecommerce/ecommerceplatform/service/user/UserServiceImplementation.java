package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;
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

    //TODO: Make it private
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    @Transactional
    public User registerUser(UserRequestDTO userRequestDTO) throws AccessDeniedException {
        User user = UserMapper.toEntity(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }
        if(!user.getRole().equals("ROLE_USER"))
            throw new AccessDeniedException("Access Denied! role must be 'ROLE_USER'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registerSeller(User adminUser, SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException {
        if(!adminUser.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied!");

        User user =  SellerMapper.toEntity(sellerRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }

        if(!user.getRole().equals("ROLE_SELLER"))
            throw new InvalidAttributesException("role must be 'ROLE_SELLER'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registerAdmin(User adminUser,UserRequestDTO userRequestDTO) throws AccessDeniedException {
        if(!adminUser.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied!");

        User user = UserMapper.toEntity(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }
        if(!user.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied! role must be 'ROLE_ADMIN'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public Address addAddressToUser(Long userId,Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.addAddress(address);
        return address;
    }

    @Override
    public List<Address> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAddressFromUser(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address Not Found"));
        user.removeAddress(address);
    }

    @Override
    public User getUserByID(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public Cart getCartByUserID(Long userId) {
        User user = getUserByID(userId);
        return user.getCart();
    }
}
