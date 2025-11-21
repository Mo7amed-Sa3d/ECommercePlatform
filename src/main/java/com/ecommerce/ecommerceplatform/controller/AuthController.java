package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.entity.User;
import com.ecommerce.ecommerceplatform.service.user.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserServices userServices;
    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/processRegister")
    public String processRegister(@ModelAttribute User user) {
        user.setRole("ROLE_USER");
        userServices.registerUser(user);
        return "redirect:/login?success";
    }
}
