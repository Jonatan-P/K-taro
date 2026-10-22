package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Redirige al login ubicado en templates/auth/login.html
    @GetMapping({"/admin", "/admin/login", "/login"})
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/admin/login")
    public String processAdminLogin(@RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam("password") String password,
                                     HttpSession session,
                                     Model model) {

        String userAccount = (email != null && !email.isBlank()) ? email : username;

        // Credenciales de Administrador para acceder al catálogo
        if ("admin@ktaro.com".equalsIgnoreCase(userAccount) && "admin123".equals(password)) {
            session.setAttribute("adminUser", userAccount);
            return "redirect:/admin/catalogo";
        }

        model.addAttribute("error", "Correo o contraseña de administrador incorrectos");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}