package com.ecommerce.ecommerceplatform.utility;

import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserUtility{

    private final UserServices userServices;

    @Autowired
    public UserUtility(UserServices userServices) {
        this.userServices = userServices;
    }

    /**
     * Get the currently authenticated user from Authentication object.
     *
     * @param authentication Spring Security authentication
     * @return User entity or null if not found
     */
    public User getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String email = authentication.getName(); // usually username/email
        return userServices.getUserByEmail(email).orElse(null);
    }

}
