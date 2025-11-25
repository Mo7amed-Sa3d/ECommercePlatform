package com.ecommerce.ecommerceplatform.service.user;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.Cart;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;

import javax.naming.directory.InvalidAttributesException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserServices {
    User registerUser(UserRequestDTO userRequestDTO) throws AccessDeniedException;
    Optional<User> getUserByEmail(String email);
    Address addAddressToUser(Long userId,Address address);
    List<Address> getAddresses(Long userId);
    void deleteAddressFromUser(Long userId,Long addressId);
    User getUserByID(Long userId);
    Cart getCartByUserID(Long userId);
    User registerSeller(User adminUser, SellerRequestDTO sellerRequestDTO) throws AccessDeniedException, InvalidAttributesException;
    User registerAdmin(User adminUser,UserRequestDTO userRequestDTO) throws AccessDeniedException;
}
