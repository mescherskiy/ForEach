package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foreach.models.ConfirmationToken;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.ConfirmationTokenRepository;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.services.ResetPasswordService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/reset_password")
@AllArgsConstructor

public class ResetPasswordController {
    private final ResetPasswordService resetPasswordService;
    private final CustomUserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public void sendLink(HttpServletResponse response,
                         @RequestParam(name = "email") String email) throws IOException {
        resetPasswordService.sendConfirmLink(email);
        response.sendRedirect("/login");
    }

    @GetMapping("/confirm")
    public String confirm(Model model,
                          @RequestParam("token") String token) {
        resetPasswordService.confirmToken(token);
        model.addAttribute("token", token);
        return "reset_password_form";
    }

    @PostMapping("/reset")
    public void changePassword(HttpServletResponse response,
                               @RequestParam("token") String token,
                               @RequestParam("newPassword") String newPassword) throws IOException {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).
                orElseThrow(() -> new IllegalArgumentException("fail"));

        CustomUser user = confirmationToken.getCustomUser();
        System.out.println(user.getId());
        Long user_id = user.getId();

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

        userRepository.updateUserPassword(encodedPassword, user_id);
        response.sendRedirect("/login");
    }
}
