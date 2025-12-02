package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper {


    public static ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setSku(product.getSku());
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setBasePrice(product.getBasePrice());
        productResponseDTO.setActive(product.getActive());
        productResponseDTO.setAttributes(product.getAttributes());
        productResponseDTO.setCreatedAt(product.getCreatedAt());
        return productResponseDTO;
    }

    public static List<ProductResponseDTO> toDTOList(List<Product> all) {
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for(Product product : all) {
            productResponseDTOList.add(toDTO(product));
        }
        return productResponseDTOList;
    }

    public static Product toEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setSku(productRequestDTO.getSku());
        product.setTitle(productRequestDTO.getTitle());
        product.setDescription(productRequestDTO.getDescription());
        product.setBasePrice(productRequestDTO.getBasePrice());
        product.setActive(productRequestDTO.getActive());
        product.setAttributes(productRequestDTO.getAttributes());
        product.setCreatedAt(Instant.now());
        return product;
    }
}
