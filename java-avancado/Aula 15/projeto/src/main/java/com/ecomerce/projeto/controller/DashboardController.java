package com.ecomerce.projeto.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {
	
	@GetMapping("/dashboard")
    public String dashboard() {
        String email = "Guest"; // Caso não haja usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
        	email = authentication.getName();
        }
        
        return "Hello " + email;
    }

}
