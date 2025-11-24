package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.SellerRequestDTO;
import com.ecommerce.ecommerceplatform.dto.requestdto.UserRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.SellerResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;

import java.time.Instant;

public class SellerMapper {

    public static SellerResponseDTO toDto(Seller seller) {
        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setSellerName(seller.getSellerName());
        sellerResponseDTO.setVerified(seller.getVerified());
        sellerResponseDTO.setCreatedAt(seller.getCreatedAt());
        return sellerResponseDTO;
    }
    public static User toEntity(SellerRequestDTO userData) {
        User user = UserMapper.toEntity(userData);

        Seller seller = new Seller();
        seller.setSellerName(userData.getSellerName());
        seller.setVerified(userData.isVerified());
        seller.setCreatedAt(Instant.now());

        seller.setUser(user);
        return user;
    }
}
