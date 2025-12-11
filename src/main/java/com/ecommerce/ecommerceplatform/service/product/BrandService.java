package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandResponseDTO> findAll();
    BrandResponseDTO findById(Long brandId);
    BrandResponseDTO createBrand(BrandRequestDTO brandRequestDTO) throws AccessDeniedException;
    String addBrandImage(MultipartFile image,Long brandId) throws IOException;
}
