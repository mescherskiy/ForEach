package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.services.RegistrationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void register (@RequestParam(name = "login") String login,
                          @RequestParam(name = "fullname") String fullName,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "language") String[] languages){
        registrationService.register(login, password, fullName, languages);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }


}
