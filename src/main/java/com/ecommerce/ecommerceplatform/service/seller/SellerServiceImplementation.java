package com.ecommerce.ecommerceplatform.service.seller;

import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImplementation implements SellerService {

    UserServices userServices;

    @Autowired
    public SellerServiceImplementation(UserServices userServices) {
        this.userServices = userServices;
    }

    @Override
    public Seller findSellerByEmail(String email) {
        var user = userServices.getUserByEmail(email);
        if(user.isEmpty() || user.get().getSeller() == null)
            throw new RuntimeException("Seller not found");
        return user.get().getSeller();
    }
}
