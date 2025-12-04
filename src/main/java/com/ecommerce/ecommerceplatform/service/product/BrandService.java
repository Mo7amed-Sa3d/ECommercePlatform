package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface BrandService {
    public List<Brand> findAll();
    public Brand findById(Long brandId);
    public Brand createBrand(BrandRequestDTO brandRequestDTO, User user) throws AccessDeniedException;

    public String addBrandImage(MultipartFile image,Long brandId, User user) throws IOException;
}
