package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.configuration.cache.CacheNames;
import com.ecommerce.ecommerceplatform.dto.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.entity.BrandImage;
import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.BrandRepository;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandServiceImplementation implements BrandService {

    private final BrandRepository brandRepository;
    private final UserUtility userUtility;

    @Value("${brand-image.upload-dir}")
    private String brandImagesUploadDirectory;

    public BrandServiceImplementation(BrandRepository brandRepository, UserUtility userUtility) {
        this.brandRepository = brandRepository;
        this.userUtility = userUtility;
    }

    @Override
    @Cacheable(value = CacheNames.brandList)
    public List<BrandResponseDTO> findAll() {
        return BrandMapper.toDTOList(brandRepository.findAll());
    }

    @Override
    @Cacheable(value = CacheNames.brands, key = "#brandId")
    public BrandResponseDTO findById(Long brandId) {
        Brand brand = getBrandOrThrow(brandId);
        return BrandMapper.toDTO(brand);
    }

    @Override
    @Transactional
    @Caching(
        put = @CachePut(value = CacheNames.brands, key = "#result.id"),
        evict = @CacheEvict(value = CacheNames.brandList, allEntries = true)
    )
    public BrandResponseDTO createBrand(BrandRequestDTO brandRequestDTO) throws AccessDeniedException {
        ensureCurrentUserIsAdmin();

        Brand brand = BrandMapper.toEntity(brandRequestDTO);
        brand.setCreatedAt(Instant.now());
        Brand savedBrand = brandRepository.save(brand);

        return BrandMapper.toDTO(savedBrand);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNames.brands, key = "#brandId"),
            @CacheEvict(value = CacheNames.brandList, allEntries = true)
    })
    public String addBrandImage(MultipartFile image, Long brandId) throws IOException {
        ensureCurrentUserIsAdmin();

        Brand brand = getBrandOrThrow(brandId);

        String directoryPath = buildBrandImageDirectory(brandId);
        createDirectoryIfNotExists(directoryPath);

        String fileName = generateUniqueFileName(image.getOriginalFilename());
        Path filePath = saveFileToDisk(image, directoryPath, fileName);

        String url = buildImageUrl(brandId, fileName);
        attachBrandImage(brand, url);

        brandRepository.save(brand);
        return url;
    }

    // ------------------------------------------------------------
    // Helper Methods
    // ------------------------------------------------------------

    private void ensureCurrentUserIsAdmin() throws AccessDeniedException {
        User user = userUtility.getCurrentUser();
        if (!"ROLE_ADMIN".equals(user.getRole())) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private Brand getBrandOrThrow(Long brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isEmpty()) {
            throw new EntityNotFoundException("Brand with id " + brandId + " not found");
        }
        return brand.get();
    }

    private String buildBrandImageDirectory(Long brandId) {
        return brandImagesUploadDirectory + "/brands/" + brandId + "/";
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    private Path saveFileToDisk(MultipartFile image, String directoryPath, String fileName) throws IOException {
        Path path = Paths.get(directoryPath + fileName);
        Files.copy(image.getInputStream(), path);
        return path;
    }

    private String buildImageUrl(Long brandId, String fileName) {
        return "/images/brands/" + brandId + "/" + fileName;
    }

    private void attachBrandImage(Brand brand, String url) {
        BrandImage brandImage = new BrandImage();
        brandImage.setUrl(url);
        brand.setBrandImage(brandImage);
    }
}
