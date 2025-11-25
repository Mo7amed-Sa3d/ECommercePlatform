package com.ecommerce.ecommerceplatform.service.order;

import com.ecommerce.ecommerceplatform.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

interface AddressRepository extends JpaRepository<Address, Long> {
}
