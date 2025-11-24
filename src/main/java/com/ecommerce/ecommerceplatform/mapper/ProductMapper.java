package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product toEntity(ProductResponseDTO productResponseDTO) {
        Product product = new Product();
        product.setId(productResponseDTO.getId());
        product.setSku(productResponseDTO.getSku());
        product.setTitle(productResponseDTO.getTitle());
        product.setDescription(productResponseDTO.getDescription());
        product.setBasePrice(productResponseDTO.getBasePrice());
        product.setActive(productResponseDTO.getActive());
        product.setAttributes(productResponseDTO.getAttributes());
        product.setCreatedAt(productResponseDTO.getCreatedAt());
        return product;
    }

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
}
