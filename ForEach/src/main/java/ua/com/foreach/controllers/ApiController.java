package ua.com.foreach.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.services.GeneralService;

import java.util.List;

@Controller
@RequestMapping("api/")
public class ApiController {

    private final GeneralService generalService;

    public ApiController(GeneralService generalService) {
        this.generalService = generalService;
    }


    @GetMapping("profile")
    public String profile(Model model) {
        UserDetails user = getCurrentUser();
        String email = user.getUsername();
        CustomUser customUser = generalService.findByLogin(email);

        model.addAttribute("email", email);
        model.addAttribute("firstName", customUser.getFirstName());
        model.addAttribute("lastName", customUser.getLastName());
        model.addAttribute("role", user.getAuthorities());
        model.addAttribute("languages", customUser.getLanguages());

        return "profile";
    }

    @GetMapping("profile/update")
    public String getUpdatePage(Model model) {
        UserDetails user = getCurrentUser();
        CustomUser customUser = generalService.findByLogin(user.getUsername());
        model.addAttribute("firstName", customUser.getFirstName());
        model.addAttribute("lastName", customUser.getLastName());

        return "update"; }


    @PostMapping("profile/update")
    public String update(@RequestParam(required = false) String firstName,
                         @RequestParam(required = false) String lastName) {
        UserDetails user = getCurrentUser();

        String login = user.getUsername();
        generalService.updateUser(login, firstName, lastName);

        return "redirect:/api/profile";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        List<CustomUser> users = generalService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable Long id, Model model) {
        CustomUser user = generalService.getById(id);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("languages", user.getLanguages());
        return "user";
    }

    private UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
