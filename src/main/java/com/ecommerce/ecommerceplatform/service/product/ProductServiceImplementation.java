package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.*;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductServiceImplementation implements ProductService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final UserUtility userUtility;

    @Value("${product-image.upload-dir}")
    private String productImagesUploadDirectory;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository,
                                        ProductImageRepository productImageRepository,
                                        CategoryService categoryService,
                                        BrandRepository brandRepository,
                                        CategoryRepository categoryRepository,
                                        UserUtility userUtility) {

        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.userUtility = userUtility;
    }

    // -------------------------------------------------------------------------
    // Main Service Methods
    // -------------------------------------------------------------------------

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return ProductMapper.toDTOList(productRepository.findByActiveTrue());
    }

    @Override
    @Cacheable(value = "product", key = "#productId")
    public ProductResponseDTO getProductById(Long productId) {
        System.err.println("Cache Miss!");
        Product product = getActiveProductOrThrow(productId);
        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(Product product, Seller seller) {
        seller.addProduct(product);
        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public void removeProduct(Long productId) {
        Product product = getProductOrThrow(productId);
        ensureCurrentUserIsProductOwner(product);

        product.setActive(false);
    }

    @Override
    @Transactional
    public List<String> saveProductImage(List<MultipartFile> imageList, Long productId) throws IOException {

        Product product = getProductOrThrow(productId);
        User user = userUtility.getCurrentUser();

        ensureUserCanModifyProduct(user, product);
        String productDir = createProductDirectory(productId);

        List<String> urlList = new ArrayList<>();

        for (MultipartFile image : imageList) {
            String fileName = generateUniqueFileName(image);
            Path filePath = Paths.get(productDir + fileName);

            Files.copy(image.getInputStream(), filePath);

            String url = "/images/products/" + productId + "/" + fileName;
            ProductImage productImage = new ProductImage(product, url);

            product.addImage(productImage);
            urlList.add(url);
        }

        productRepository.save(product);
        return urlList;
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(Product product, Long brandId, Long categoryId) {
        Seller seller = userUtility.getCurrentUser().getSeller();

        Brand brand = brandRepository.findById(brandId).get();
        Category category = categoryRepository.findById(categoryId).get();

        brand.addProduct(product);
        category.addProduct(product);
        seller.addProduct(product);

        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    @Override
    public List<ProductResponseDTO> findAllByCategoryId(Long categoryId) {
        return ProductMapper.toDTOList(productRepository.findAllProductsByCategory(categoryId));
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO dto) {

        Product product = getProductOrThrow(productId);
        ensureCurrentUserIsProductOwner(product);

        product.setSku(dto.getSku());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setAttributes(dto.getAttributes());
        product.setBasePrice(dto.getBasePrice());
        product.setActive(dto.getActive());
        product.setCreatedAt(dto.getCreatedAt());

        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public String deleteProductImage(Long productId, Long imageId) throws IOException {

        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Product image not found"));

        if (!image.getProduct().getId().equals(productId)) {
            throw new EntityNotFoundException("Product image not found");
        }

        Product product = image.getProduct();
        ensureCurrentUserIsProductOwner(product);

        deleteImageFile(productId, image.getUrl());

        product.removeImage(image);
        productImageRepository.delete(image);

        return "Done deleting image with id " + imageId +
                " for product with id " + productId;
    }

    // -------------------------------------------------------------------------
    // Helper Methods (Clean Code)
    // -------------------------------------------------------------------------

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private Product getActiveProductOrThrow(Long id) {
        return productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private void ensureCurrentUserIsProductOwner(Product product) {
        User user = userUtility.getCurrentUser();
        if (!product.getSeller().getId().equals(user.getSeller().getId())) {
            throw new EntityNotFoundException("Product not found");
        }
    }

    private void ensureUserCanModifyProduct(User user, Product product) {
        boolean isAdmin = user.getRole().equals("ROLE_ADMIN");
        boolean isOwner = product.getSeller().getId().equals(user.getSeller().getId());

        if (!isAdmin && !isOwner) {
            throw new EntityNotFoundException("ACCESS DENIED!");
        }
    }

    private String createProductDirectory(Long productId) {
        String productDir = productImagesUploadDirectory + "/products/" + productId + "/";
        File dir = new File(productDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return productDir;
    }

    private String generateUniqueFileName(MultipartFile image) {
        return UUID.randomUUID() + "_" + image.getOriginalFilename();
    }

    private void deleteImageFile(Long productId, String url) throws IOException {
        String filename = url.substring(url.lastIndexOf("/") + 1);

        String filePath = productImagesUploadDirectory +
                "/products/" + productId + "/" + filename;

        Files.deleteIfExists(Paths.get(filePath));
    }
}
