package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.SellerResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;

public class SellerMapper {

    public static SellerResponseDTO toDto(Seller seller) {
        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setSellerName(seller.getSellerName());
        sellerResponseDTO.setVerified(seller.getVerified());
        sellerResponseDTO.setCreatedAt(seller.getCreatedAt());
        return sellerResponseDTO;
    }
}
