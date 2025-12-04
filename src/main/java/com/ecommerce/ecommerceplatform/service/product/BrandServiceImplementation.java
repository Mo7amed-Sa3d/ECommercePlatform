package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.BrandImage;
import com.ecommerce.ecommerceplatform.entity.ProductImage;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.dto.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandServiceImplementation implements BrandService {

    BrandRepository brandRepository;

    @Value("${brand-image.upload-dir}")
    private String brandImagesUploadDirectory;


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

    @Override
    public String addBrandImage(MultipartFile image, Long brandId, User user) throws IOException {

        if(user.getRole().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Access denied");

        String brandDir = brandImagesUploadDirectory + "/brands/" + brandId + "/";
        File dir = new File(brandDir);
        if (!dir.exists())
            dir.mkdirs();

        var optional = brandRepository.findById(brandId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Brand with id " + brandId + " not found");
        var brand = optional.get();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(brandDir + fileName);
        Files.copy(image.getInputStream(), path);
        String url = "/images/brand/" + brandId + "/" + fileName;

        BrandImage brandImage = new BrandImage();

        brandImage.setUrl(url);
        brand.setBrandImage(brandImage);

        brandRepository.save(brand);
        return url;
    }


}
