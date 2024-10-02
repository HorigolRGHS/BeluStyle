package com.emc.belustyle.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/home";
        }
        return "login-form";
    }

    @GetMapping("/home")
    public String home() {
        return "home-page";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }


}
