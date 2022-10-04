package ua.com.foreach.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.model.CustomUser;
import ua.com.foreach.services.UserService;

import java.util.List;


@Controller
@RequestMapping("api/")
@AllArgsConstructor
public class ApiController {

    private final UserService userService;

    @GetMapping("profile")
    public String profile(Model model){
        UserDetails currentUser = getCurrentUser();
        String email = currentUser.getUsername();
        CustomUser customUser = userService.findByLogin(email);

        model.addAttribute("email", email);
        model.addAttribute("firstName", customUser.getFirstName());
        model.addAttribute("lastName", customUser.getLastName());
        model.addAttribute("role", currentUser.getAuthorities());
        model.addAttribute("languages", customUser.getLanguages());

        return "profile";
    }

    @GetMapping("profile/update")
    public String getUpdatePage(Model model){
        UserDetails user = getCurrentUser();
        CustomUser customUser = userService.findByLogin(user.getUsername());
        model.addAttribute("firstName", customUser.getFirstName());
        model.addAttribute("lastName", customUser.getLastName());

        return "update";
    }

    @PostMapping("profile/update")
    public String update(@RequestParam(required = false) String firstName,
                         @RequestParam(required = false) String lastName) {
        UserDetails user = getCurrentUser();

        String login = user.getUsername();
        userService.updateUser(login, firstName, lastName);

        return "redirect:/api/profile";
    }

    @GetMapping("/users")
    public String getAll(Model model){
        List<CustomUser> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable Long id, Model model){
        CustomUser user = userService.getById(id);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("languages", user.getLanguages());
        return "user";
    }

    private UserDetails getCurrentUser(){
        return (UserDetails) SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();
    }
}
