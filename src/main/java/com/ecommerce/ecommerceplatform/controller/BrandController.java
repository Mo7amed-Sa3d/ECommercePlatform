package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.BrandRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.BrandResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Brand;
import com.ecommerce.ecommerceplatform.mapper.BrandMapper;
import com.ecommerce.ecommerceplatform.service.product.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    BrandService brandService;
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
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
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandRequestDTO brandRequestDTO) {
        Brand createdBrand = brandService.createBrand(brandRequestDTO);
        return ResponseEntity.ok(BrandMapper.toDTO(createdBrand));
    }

}
