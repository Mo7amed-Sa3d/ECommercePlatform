package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.BrandImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandImageRepository extends JpaRepository<BrandImage, Integer> {
}
