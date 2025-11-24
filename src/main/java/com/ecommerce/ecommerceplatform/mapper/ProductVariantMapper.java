package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ProductVariantResponseDTO;
import com.ecommerce.ecommerceplatform.entity.ProductVariant;

public class ProductVariantMapper {
    public static ProductVariantResponseDTO toDTO(ProductVariant productVariant) {
        ProductVariantResponseDTO productVariantResponseDTO = new ProductVariantResponseDTO();
        productVariantResponseDTO.setId(productVariant.getId());
        productVariantResponseDTO.setSku(productVariant.getSku());
        productVariantResponseDTO.setPrice(productVariant.getPrice());
        productVariantResponseDTO.setAttributes(productVariant.getAttributes());
        productVariantResponseDTO.setWeightGrams(productVariant.getWeightGrams());
        productVariantResponseDTO.setStockSize(productVariant.getStockSize());
        return productVariantResponseDTO;
    }

}
