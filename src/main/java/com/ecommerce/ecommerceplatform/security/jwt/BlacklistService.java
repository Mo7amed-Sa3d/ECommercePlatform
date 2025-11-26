package com.ecommerce.ecommerceplatform.security.jwt;

import com.ecommerce.ecommerceplatform.entity.BlacklistedToken;
import com.ecommerce.ecommerceplatform.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlacklistService {

    private final BlacklistedTokenRepository repository;

    @Autowired
    public BlacklistService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.repository = blacklistedTokenRepository;
    }

    @Transactional
    public void blackListToken(String token){
        if(repository.findByToken(token).isEmpty()){
            BlacklistedToken blacklistedToken = new BlacklistedToken();
            blacklistedToken.setToken(token);
            repository.save(blacklistedToken);
        }
    }

    public boolean isBlackListed(String token){
        return repository.findByToken(token).isPresent();
    }
}
