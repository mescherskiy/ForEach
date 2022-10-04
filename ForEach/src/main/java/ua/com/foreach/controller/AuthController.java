package ua.com.foreach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String getIndexPage() { return "index"; }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {return "registration"; }

    @GetMapping("/reset_password")
    public String getResetPasswordPage() {return "reset_password_page"; }
}
