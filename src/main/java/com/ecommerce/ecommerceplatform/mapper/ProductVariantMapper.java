package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.ProductVariantDTO;
import com.ecommerce.ecommerceplatform.entity.ProductVariant;

public class ProductVariantMapper {
    public static ProductVariantDTO toDTO(ProductVariant productVariant) {
        ProductVariantDTO productVariantDTO = new ProductVariantDTO();
        productVariantDTO.setId(productVariant.getId());
        productVariantDTO.setSku(productVariant.getSku());
        productVariantDTO.setPrice(productVariant.getPrice());
        productVariantDTO.setAttributes(productVariant.getAttributes());
        productVariantDTO.setWeightGrams(productVariant.getWeightGrams());
        productVariantDTO.setStockSize(productVariant.getStockSize());
        return productVariantDTO;
    }

    public static ProductVariant toEntity(ProductVariantDTO productVariantDTO) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantDTO.getId());
        productVariant.setSku(productVariantDTO.getSku());
        productVariant.setPrice(productVariantDTO.getPrice());
        productVariant.setAttributes(productVariantDTO.getAttributes());
        productVariant.setWeightGrams(productVariantDTO.getWeightGrams());
        productVariant.setStockSize(productVariantDTO.getStockSize());
        return productVariant;
    }
}
