package com.ecommerce.ecommerceplatform.DAO;

import com.ecommerce.ecommerceplatform.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
