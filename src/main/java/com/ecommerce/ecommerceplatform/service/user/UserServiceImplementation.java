package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.dto.mapper.AddressMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.CartMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.dto.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import com.ecommerce.ecommerceplatform.service.payment.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class UserServiceImplementation implements UserServices {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;
    public UserServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder
                                     , AddressRepository addressRepository, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.paymentService = paymentService;
    }

    //TODO: Make it private
    @Override
    public UserResponseDTO getUserByEmail(String email) {
        var optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException("User with email " + email + " not found");
        return UserMapper.toDto(optionalUser.get());
    }


    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) throws AccessDeniedException {
        User user = UserMapper.toEntity(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }
        if(!user.getRole().equals("ROLE_USER"))
            throw new AccessDeniedException("Access Denied! role must be 'ROLE_USER'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO registerSeller(User adminUser, SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException, StripeException {
        if(!adminUser.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied!");

        User user =  SellerMapper.toEntity(sellerRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }

        if(!user.getRole().equals("ROLE_SELLER"))
            throw new InvalidAttributesException("role must be 'ROLE_SELLER'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String paymentAccountId = paymentService.createSellerAccount().getId();
        user.getSeller().setPaymentAccountId(paymentAccountId);
        System.err.println(paymentAccountId);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO registerAdmin(User adminUser,UserRequestDTO userRequestDTO) throws AccessDeniedException {
        if(!adminUser.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied!");

        User user = UserMapper.toEntity(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }
        if(!user.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access Denied! role must be 'ROLE_ADMIN'");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }


    @Override
    @Transactional
    public AddressResponseDTO addAddressToUser(Long userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.addAddress(address);
        return AddressMapper.toDto(address);
    }

    @Override
    public List<AddressResponseDTO> getAddresses(Long userId) {
        return AddressMapper.toDtoList(addressRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public void deleteAddressFromUser(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address Not Found"));
        user.removeAddress(address);
    }

    @Override
    public UserResponseDTO getUserByID(Long userId) {
        var user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found");
        return UserMapper.toDto(user.get());
    }

    @Override
    public CartResponseDTO getCartByUserID(Long userId) {
        var user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found");
        return CartMapper.toDTO(user.get().getCart());
    }
}
