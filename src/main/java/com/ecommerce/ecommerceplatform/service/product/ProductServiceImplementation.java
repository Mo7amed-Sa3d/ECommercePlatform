package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.entity.*;
import com.ecommerce.ecommerceplatform.repository.ProductImageRepository;
import com.ecommerce.ecommerceplatform.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImplementation implements ProductService {

    private final BrandService brandService;
    private final CategoryService categoryService;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    @Value("${product-image.upload-dir}")
    private String productImagesUploadDirectory;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, ProductImageRepository productImageRepository, BrandService brandService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> optional = productRepository.findById(productId);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Product not found");
        return optional.get();
    }

    @Override
    public Product saveProduct(Product product, Seller seller) {
        seller.addProduct(product);
        return productRepository.save(product);
    }

    @Override
    public void removeProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductImage saveProductImage(MultipartFile file, Long productId) throws IOException {

        String productDir = productImagesUploadDirectory + "/products/" + productId + "/";
        File dir = new File(productDir);
        if (!dir.exists())
            dir.mkdirs();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        System.out.println(file.getOriginalFilename());
        Path path = Paths.get(productDir + fileName);
        Files.copy(file.getInputStream(), path);
        String url = "/images/products/" + productId + "/" + fileName;

        Product product = getProductById(productId);
        ProductImage productImage = new ProductImage(product,url);

        product.addImage(productImage);
        productImageRepository.save(productImage);

        return productImage;
    }

    @Override
    public Product saveProduct(Product product, Long brandId, Long categoryId, Seller seller) {
        Brand brand = brandService.findById(brandId);
        Category category = categoryService.findById(categoryId);
        brand.addProduct(product);
        category.addProduct(product);
        seller.addProduct(product);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        return productRepository.findAllProductsByCategory(categoryId);
    }
}
