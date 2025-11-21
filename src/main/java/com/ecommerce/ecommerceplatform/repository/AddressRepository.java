package com.ecommerce.ecommerceplatform.repository;

import com.ecommerce.ecommerceplatform.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}
