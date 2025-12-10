package com.ecommerce.ecommerceplatform.service.seller;

import com.ecommerce.ecommerceplatform.dto.responsedto.SellerResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    SellerResponseDTO findSellerByEmail(String email);
    SellerResponseDTO findSellerByUserId(Long userId);
}
