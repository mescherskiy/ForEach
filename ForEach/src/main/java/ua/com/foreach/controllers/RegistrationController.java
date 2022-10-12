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
    public void register (@RequestParam(name = "email") String email,
                          @RequestParam(name = "firstname") String firstName,
                          @RequestParam(name = "lastname") String lastName,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "language") String[] languages){
        registrationService.register(email, password, firstName, lastName, languages);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }


}
