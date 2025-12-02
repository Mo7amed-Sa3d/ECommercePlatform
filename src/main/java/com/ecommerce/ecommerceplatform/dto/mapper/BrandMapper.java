package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BrandMapper {

    public static BrandResponseDTO toDTO(Brand brand) {
        BrandResponseDTO brandResponseDTO = new BrandResponseDTO();
        brandResponseDTO.setId(brand.getId());
        brandResponseDTO.setName(brand.getName());
        brandResponseDTO.setDescription(brand.getDescription());
        brandResponseDTO.setCountry(brand.getCountry());
        brandResponseDTO.setCreatedAt(brand.getCreatedAt());
        return brandResponseDTO;
    }

    public static List<BrandResponseDTO> toDTOList(List<Brand> all) {
        List<BrandResponseDTO> brandResponseDTOList = new ArrayList<>();
        for (Brand brand : all) {
            brandResponseDTOList.add(toDTO(brand));
        }
        return brandResponseDTOList;
    }

    public static Brand toEntity(BrandRequestDTO brandRequestDTO) {
        Brand brand = new Brand();
        brand.setName(brandRequestDTO.getName());
        brand.setDescription(brandRequestDTO.getDescription());
        brand.setCountry(brandRequestDTO.getCountry());
        brand.setCreatedAt(Instant.now());
        return brand;
    }
}
