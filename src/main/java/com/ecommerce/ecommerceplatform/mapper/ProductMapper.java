package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.ProductDTO;
import com.ecommerce.ecommerceplatform.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setSku(productDTO.getSku());
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setBasePrice(productDTO.getBasePrice());
        product.setActive(productDTO.getActive());
        product.setAttributes(productDTO.getAttributes());
        product.setCreatedAt(productDTO.getCreatedAt());
        return product;
    }

    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setSku(product.getSku());
        productDTO.setTitle(product.getTitle());
        productDTO.setDescription(product.getDescription());
        productDTO.setBasePrice(product.getBasePrice());
        productDTO.setActive(product.getActive());
        productDTO.setAttributes(product.getAttributes());
        productDTO.setCreatedAt(product.getCreatedAt());
        return productDTO;
    }
}
