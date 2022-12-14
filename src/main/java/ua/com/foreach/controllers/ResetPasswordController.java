package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.models.ConfirmationToken;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.ConfirmationTokenRepository;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.services.ResetPasswordService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/reset_password")

public class ResetPasswordController {
    private final ResetPasswordService resetPasswordService;
    private final CustomUserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public void sendLink(HttpServletResponse response,
                         @RequestParam(name = "login") String login) throws IOException {
        resetPasswordService.sendConfirmLink(login);
        response.sendRedirect("/login");
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        resetPasswordService.confirmToken(token);
        return token;
    }

    @PostMapping("/reset")
    public void changePassword(@RequestParam("token") String token,
                                     @RequestParam("newPassword") String newPassword) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).
                orElseThrow(() -> new IllegalArgumentException("fail"));

        CustomUser user = confirmationToken.getCustomUser();
        System.out.println(user.getId());
        Long user_id = user.getId();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        userRepository.updateUserPassword(encodedPassword, user_id);
    }
}
