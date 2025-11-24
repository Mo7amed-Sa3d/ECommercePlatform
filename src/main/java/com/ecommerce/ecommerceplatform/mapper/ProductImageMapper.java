package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ProductImageResponseDTO;
import com.ecommerce.ecommerceplatform.entity.ProductImage;

public class ProductImageMapper {

    public static ProductImageResponseDTO toDTO(ProductImage productImage) {
        ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
        productImageResponseDTO.setId(productImage.getId());
        productImageResponseDTO.setUrl(productImage.getUrl());
        productImageResponseDTO.setAltText(productImage.getAltText());
        productImageResponseDTO.setDisplayOrder(productImage.getDisplayOrder());
        return productImageResponseDTO;
    }
}
