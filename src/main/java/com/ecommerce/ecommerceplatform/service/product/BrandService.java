package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.Product;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    public List<Brand> findAll();
    public Brand findById(Long brandId);
    public Brand createBrand(BrandRequestDTO brandRequestDTO);
}
