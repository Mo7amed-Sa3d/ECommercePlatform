package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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
    public Brand createBrand(BrandRequestDTO brandRequestDTO) {
        Brand brand = BrandMapper.toEntity(brandRequestDTO);
        return brandRepository.save(brand);
    }

}
