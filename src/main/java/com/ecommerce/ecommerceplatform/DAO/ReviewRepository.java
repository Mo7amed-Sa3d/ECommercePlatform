package com.ecommerce.ecommerceplatform.DAO;

import com.ecommerce.ecommerceplatform.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
