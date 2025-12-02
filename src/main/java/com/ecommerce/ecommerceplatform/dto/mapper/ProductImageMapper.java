package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ProductImageResponseDTO;
import com.ecommerce.ecommerceplatform.entity.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class ProductImageMapper {

    public static ProductImageResponseDTO toDTO(ProductImage productImage) {
        ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
        productImageResponseDTO.setId(productImage.getId());
        productImageResponseDTO.setUrl(productImage.getUrl());
        productImageResponseDTO.setAltText(productImage.getAltText());
        productImageResponseDTO.setDisplayOrder(productImage.getDisplayOrder());
        return productImageResponseDTO;
    }

    public static List<ProductImageResponseDTO> toDTOLList(List<ProductImage> productImageList) {
        List<ProductImageResponseDTO> productImageResponseDTOList = new ArrayList<>();
        for (ProductImage productImage : productImageList) {
            ProductImageResponseDTO productImageResponseDTO = toDTO(productImage);
            productImageResponseDTOList.add(productImageResponseDTO);
        }
        return productImageResponseDTOList;
    }
}
