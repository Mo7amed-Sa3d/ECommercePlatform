package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.service.product.BrandService;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final UserUtility userUtility;
    BrandService brandService;
    public BrandController(BrandService brandService, UserUtility userUtility) {
        this.brandService = brandService;
        this.userUtility = userUtility;
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(BrandMapper.toDTOList(brandService.findAll()));
    }

    @GetMapping({"/{brandId}"})
    public ResponseEntity<BrandResponseDTO> getAllBrandsByBrandId(@PathVariable Long brandId) {
        return ResponseEntity.ok(BrandMapper.toDTO(brandService.findById(brandId)));
    }

    @PostMapping
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandRequestDTO brandRequestDTO) throws AccessDeniedException {
        var user = userUtility.getCurrentUser();
        return ResponseEntity.ok(BrandMapper.toDTO(brandService.createBrand(brandRequestDTO,user)));
    }

}
