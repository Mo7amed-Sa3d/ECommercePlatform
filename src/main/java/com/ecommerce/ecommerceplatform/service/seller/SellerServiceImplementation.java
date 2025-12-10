package com.ecommerce.ecommerceplatform.service.seller;

import com.ecommerce.ecommerceplatform.dto.mapper.SellerMapper;
import com.ecommerce.ecommerceplatform.dto.responsedto.SellerResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImplementation implements SellerService {

    private final UserRepository userRepository;
    UserServices userServices;

    @Autowired
    public SellerServiceImplementation(UserServices userServices, UserRepository userRepository) {
        this.userServices = userServices;
        this.userRepository = userRepository;
    }

    @Override
    public SellerResponseDTO findSellerByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty() || user.get().getSeller() == null)
            throw new RuntimeException("Seller not found");
        return SellerMapper.toDto(user.get().getSeller());
    }

    @Override
    public SellerResponseDTO findSellerByUserId(Long userId) {
        return SellerMapper.toDto(userRepository.findById(userId).get().getSeller());
    }


}
