package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.CartResponseDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.UserResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import com.stripe.exception.StripeException;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserServices {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO) throws AccessDeniedException;
    UserResponseDTO getUserByEmail(String email);
    AddressResponseDTO addAddressToUser(Address address);
    List<AddressResponseDTO> getAddresses();
    void deleteAddressFromUser(Long addressId);
    UserResponseDTO getUserByID(Long userId);
    CartResponseDTO getCartByUserID(Long userId);
    UserResponseDTO registerSeller(SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException, StripeException;
    UserResponseDTO registerAdmin(UserRequestDTO userRequestDTO) throws AccessDeniedException;
}
