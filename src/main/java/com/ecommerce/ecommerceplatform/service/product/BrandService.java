package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.Product;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand createBrand(Brand brand);
    Brand updateBrand(Brand brand);
    void deleteBrand(Long brandId);
    Optional<Brand> getBrandById(Long brandId);
    List<Brand> getAllBrands();

    void addProductToBrand(Long brandId, Long productId);
    void removeProductFromBrand(Long brandId, Long productId);
    List<Product> getBrandProducts(Long brandId);
}
