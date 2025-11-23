package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.BrandDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;

public class BrandMapper {

    public static Brand toEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setCountry(brandDTO.getCountry());
        brand.setCreatedAt(brandDTO.getCreatedAt());
        return brand;
    }

    public static BrandDTO toDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        brandDTO.setCountry(brand.getCountry());
        brandDTO.setCreatedAt(brand.getCreatedAt());
        return brandDTO;
    }
}
