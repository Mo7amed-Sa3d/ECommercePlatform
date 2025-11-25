package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImplementation implements BrandService {

    BrandRepository brandRepository;
    public BrandServiceImplementation(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if(brand.isEmpty())
            throw new EntityNotFoundException("Brand with id " + brandId + " not found");
        return brand.get();
    }

    @Override
    @Transactional
    public Brand createBrand(BrandRequestDTO brandRequestDTO, User user) throws AccessDeniedException {
        if(!user.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access denied");
        Brand brand = BrandMapper.toEntity(brandRequestDTO);
        return brandRepository.save(brand);
    }
}
