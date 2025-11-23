package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.ProductImageDTO;
import com.ecommerce.ecommerceplatform.entity.ProductImage;

public class ProductImageMapper {
    public static ProductImage toEntity(ProductImageDTO productImageDTO) {
        ProductImage productImage = new ProductImage();
        productImage.setId(productImageDTO.getId());
        productImage.setUrl(productImageDTO.getUrl());
        productImage.setAltText(productImageDTO.getAltText());
        productImage.setDisplayOrder(productImageDTO.getDisplayOrder());
        return productImage;
    }

    public static ProductImageDTO toDTO(ProductImage productImage) {
        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setId(productImage.getId());
        productImageDTO.setUrl(productImage.getUrl());
        productImageDTO.setAltText(productImage.getAltText());
        productImageDTO.setDisplayOrder(productImage.getDisplayOrder());
        return productImageDTO;
    }
}
