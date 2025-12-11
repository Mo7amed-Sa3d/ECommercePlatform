package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.ProductResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long productId);
    ProductResponseDTO saveProduct(Product product, Seller seller);
    void removeProduct(Long productId);
    List<String> saveProductImage(List<MultipartFile> imageList, Long productId) throws IOException;
    ProductResponseDTO saveProduct(Product entity, Long brandId, Long categoryId);
    List<ProductResponseDTO> findAllByCategoryId(Long categoryId);
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    String deleteProductImage(Long productId, Long imageId) throws IOException;
}
