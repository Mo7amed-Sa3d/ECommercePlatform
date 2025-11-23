package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.SellerDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;

public class SellerMapper {

    public static SellerDTO toDto(Seller seller) {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(seller.getId());
        sellerDTO.setSellerName(seller.getSellerName());
        sellerDTO.setVerified(seller.getVerified());
        sellerDTO.setCreatedAt(seller.getCreatedAt());
        return sellerDTO;
    }
}
