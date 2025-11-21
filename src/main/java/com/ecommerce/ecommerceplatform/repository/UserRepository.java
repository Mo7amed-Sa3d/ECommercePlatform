package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.Address;
import com.ecommerce.ecommerceplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
