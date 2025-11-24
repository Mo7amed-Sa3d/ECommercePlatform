package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;

public class BrandMapper {

    public static Brand toEntity(BrandResponseDTO brandResponseDTO) {
        Brand brand = new Brand();
        brand.setId(brandResponseDTO.getId());
        brand.setName(brandResponseDTO.getName());
        brand.setDescription(brandResponseDTO.getDescription());
        brand.setCountry(brandResponseDTO.getCountry());
        brand.setCreatedAt(brandResponseDTO.getCreatedAt());
        return brand;
    }

    public static BrandResponseDTO toDTO(Brand brand) {
        BrandResponseDTO brandResponseDTO = new BrandResponseDTO();
        brandResponseDTO.setId(brand.getId());
        brandResponseDTO.setName(brand.getName());
        brandResponseDTO.setDescription(brand.getDescription());
        brandResponseDTO.setCountry(brand.getCountry());
        brandResponseDTO.setCreatedAt(brand.getCreatedAt());
        return brandResponseDTO;
    }
}
