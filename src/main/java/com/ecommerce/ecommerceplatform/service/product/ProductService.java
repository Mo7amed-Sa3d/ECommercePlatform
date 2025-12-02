package com.ecommerce.ecommerceplatform.service.product;

import com.ecommerce.ecommerceplatform.dto.requestdto.ProductRequestDTO;
import com.ecommerce.ecommerceplatform.entity.Product;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    Product saveProduct(Product product, Seller seller);
    void removeProduct(Long productId, User user);
    List<String> saveProductImage(List<MultipartFile> imageList, User user, Long productId) throws IOException;
    Product saveProduct(Product entity, Long brandId, Long categoryId, Seller seller);
    List<Product> findAllByCategoryId(Long categoryId);
    Product updateProduct(User user,Long productId, ProductRequestDTO productRequestDTO);

    String deleteProductImage(User user, Long productId, Long imageId) throws IOException;
}
