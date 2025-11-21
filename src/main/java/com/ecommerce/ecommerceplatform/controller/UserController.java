package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            model.addAttribute("user", user.get());
            return "user/profile";
        }else
            return "login";
    }

    @PutMapping("/profile")
    @ResponseBody
    public User updateProfile(Authentication authentication, @RequestBody User updatedUser) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if(updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        return userRepository.save(user);
    }

}
