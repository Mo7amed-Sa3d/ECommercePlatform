package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.mapper.ProductMapper;
import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.BrandRepository;
import com.ecommerce.ecommerceplatform.repository.CategoryRepository;
import com.ecommerce.ecommerceplatform.repository.ProductImageRepository;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductServiceImplementation implements ProductService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    @Value("${product-image.upload-dir}")
    private String productImagesUploadDirectory;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, ProductImageRepository productImageRepository, CategoryService categoryService, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return ProductMapper.toDTOList(productRepository.findByActiveTrue());
    }

    @Override
    @Cacheable(value = "product", key = "#productId")
    public ProductResponseDTO getProductById(Long productId) {
        System.err.println("Cache Miss!");
        Optional<Product> optional = productRepository.findByIdAndActiveTrue(productId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        return ProductMapper.toDTO(optional.get());
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(Product product, Seller seller) {
        seller.addProduct(product);
        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public void removeProduct(Long productId,User user) {
        Optional<Product> optional = productRepository.findById(productId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        Product product = optional.get();


        if(!product.getSeller().getId().equals(user.getSeller().getId()))
            throw new EntityNotFoundException("Product not found");

        product.setActive(false);
    }

    @Override
    @Transactional
    public List<String> saveProductImage(List<MultipartFile> imageList, User user, Long productId) throws IOException {

        String productDir = productImagesUploadDirectory + "/products/" + productId + "/";
        File dir = new File(productDir);
        if (!dir.exists())
            dir.mkdirs();

        var optional = productRepository.findById(productId);
        if (optional.isEmpty())
            throw new EntityNotFoundException("Product not found");

        Product product = optional.get();

        List<String> urlList = new ArrayList<>();

        for(var image : imageList) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get(productDir + fileName);
            Files.copy(image.getInputStream(), path);
            String url = "/images/products/" + productId + "/" + fileName;


            if (!user.getRole().equals("ROLE_ADMIN") && !product.getSeller().getId().equals(user.getSeller().getId()))
                throw new EntityNotFoundException("ACCESS DENIED!");

            ProductImage productImage = new ProductImage(product, url);

            product.addImage(productImage);
            urlList.add(url);
        }
        productRepository.save(product);
        return urlList;
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(Product product, Long brandId, Long categoryId, Seller seller) {
        Brand brand = brandRepository.findById(brandId).get();
        Category category = categoryRepository.findById(categoryId).get();
        brand.addProduct(product);
        category.addProduct(product);
        seller.addProduct(product);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> findAllByCategoryId(Long categoryId) {
        return  ProductMapper.toDTOList(productRepository.findAllProductsByCategory(categoryId));
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(User user,Long productId, ProductRequestDTO productRequestDTO) {
        Optional<Product> optional = productRepository.findById(productId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        Product product = optional.get();
        if(!product.getSeller().getId().equals(user.getSeller().getId()))
            throw new EntityNotFoundException("Product not found");
        product.setSku(productRequestDTO.getSku());
        product.setTitle(productRequestDTO.getTitle());
        product.setDescription(productRequestDTO.getDescription());
        product.setAttributes(productRequestDTO.getAttributes());
        product.setBasePrice(productRequestDTO.getBasePrice());
        product.setActive(productRequestDTO.getActive());
        product.setCreatedAt(productRequestDTO.getCreatedAt());
        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public String deleteProductImage(User user, Long productId, Long imageId) throws IOException {
        var optional = productImageRepository.findById(imageId);

        if(optional.isEmpty())
            throw new EntityNotFoundException("Product image not found");
        ProductImage productImage = optional.get();

        if(!productImage.getProduct().getId().equals(productId))
            throw new EntityNotFoundException("Product image not found");

        if(!productImage.getProduct().getSeller().getId().equals(user.getSeller().getId()))
            throw new AccessDeniedException("Access Denied");


        String url = productImage.getUrl();
        String filename = url.substring(url.lastIndexOf("/") + 1);

        String filePath = productImagesUploadDirectory +
                "/products/" + productId + "/" + filename;

        Files.deleteIfExists(Paths.get(filePath));

        var product =  productImage.getProduct();

        //Break the link
        product.removeImage(productImage);

        productImageRepository.delete(productImage);


        return "Done deleting image with id " +  imageId + " for product with id " + productId;
    }


}
