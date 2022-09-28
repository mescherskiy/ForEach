package ua.com.foreach.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.model.CustomUser;
import ua.com.foreach.model.ProgrammingLanguage;
import ua.com.foreach.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void register (HttpServletResponse response, @RequestParam(name = "email") String email,
                          @RequestParam(name = "firstname") String firstName,
                          @RequestParam(name = "lastname") String lastName,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "language") String[] languages) throws IOException {
        registrationService.register(email, password, firstName, lastName, languages);
        response.sendRedirect("/login");
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }


}
