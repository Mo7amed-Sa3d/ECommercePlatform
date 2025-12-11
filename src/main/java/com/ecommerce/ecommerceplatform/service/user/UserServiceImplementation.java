package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.dto.mapper.AddressMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.CartMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.dto.mapper.UserMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.AddressRepository;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import com.ecommerce.ecommerceplatform.service.payment.PaymentService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
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
    private final UserUtility userUtility;

    public UserServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     AddressRepository addressRepository,
                                     PaymentService paymentService,
                                     UserUtility userUtility) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.paymentService = paymentService;
        this.userUtility = userUtility;
    }

    // -------------------------------------------------------------------------
    // Public Service Methods
    // -------------------------------------------------------------------------

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return UserMapper.toDto(findUserByEmailOrThrow(email));
    }

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) throws AccessDeniedException, InvalidAttributesException {
        User user = UserMapper.toEntity(dto);
        validateEmailNotExists(user.getEmail());
        ensureRole(user, "ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO registerSeller(SellerRequestDTO dto)
            throws AccessDeniedException, InvalidAttributesException, StripeException {

        ensureCurrentUserIsAdmin();

        User user = SellerMapper.toEntity(dto);
        validateEmailNotExists(user.getEmail());
        ensureRole(user, "ROLE_SELLER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String paymentAccountId = paymentService.createSellerAccount().getId();
        user.getSeller().setPaymentAccountId(paymentAccountId);

        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO registerAdmin(UserRequestDTO dto) throws AccessDeniedException, InvalidAttributesException {

        ensureCurrentUserIsAdmin();

        User user = UserMapper.toEntity(dto);
        validateEmailNotExists(user.getEmail());
        ensureRole(user, "ROLE_ADMIN");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public AddressResponseDTO addAddressToUser(Address address) {
        User user = userUtility.getCurrentUser();
        user.addAddress(address);
        return AddressMapper.toDto(address);
    }

    @Override
    public List<AddressResponseDTO> getAddresses() {
        User user = userUtility.getCurrentUser();
        return AddressMapper.toDtoList(addressRepository.findByUserId(user.getId()));
    }

    @Override
    @Transactional
    public void deleteAddressFromUser(Long addressId) {
        User user = userUtility.getCurrentUser();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address Not Found"));
        user.removeAddress(address);
    }

    @Override
    public UserResponseDTO getUserByID(Long userId) {
        return UserMapper.toDto(findUserByIdOrThrow(userId));
    }

    @Override
    public CartResponseDTO getCartByUserID(Long userId) {
        User user = findUserByIdOrThrow(userId);
        return CartMapper.toDTO(user.getCart());
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    private void ensureCurrentUserIsAdmin() throws AccessDeniedException {
        User current = userUtility.getCurrentUser();
        if (!"ROLE_ADMIN".equals(current.getRole())) {
            throw new AccessDeniedException("Access Denied!");
        }
    }

    private void ensureRole(User user, String requiredRole) throws AccessDeniedException, InvalidAttributesException {
        if (!requiredRole.equals(user.getRole())) {
            if ("ROLE_SELLER".equals(requiredRole)) {
                throw new InvalidAttributesException("Role must be 'ROLE_SELLER'");
            }
            throw new AccessDeniedException("Access Denied! role must be '" + requiredRole + "'");
        }
    }

    private void validateEmailNotExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email Already Exists");
        }
    }

    private User findUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }
}
