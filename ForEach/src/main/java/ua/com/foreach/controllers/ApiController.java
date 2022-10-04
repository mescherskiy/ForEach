package ua.com.foreach.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.Project;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.UserService;

import java.util.List;

@Controller
@RequestMapping("api/")
public class ApiController {

    private final UserService userService;
    private final ProjectService projectService;

    public ApiController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }


    @GetMapping("profile")
    public String profile(Model model) {
        UserDetails user = getCurrentUser();
        String email = user.getUsername();
        CustomUser customUser = userService.findByLogin(email);

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
        CustomUser customUser = userService.findByLogin(user.getUsername());
        model.addAttribute("firstName", customUser.getFirstName());
        model.addAttribute("lastName", customUser.getLastName());

        return "update"; }


    @PostMapping("profile/update")
    public String update(@RequestParam(required = false) String firstName,
                         @RequestParam(required = false) String lastName) {
        UserDetails user = getCurrentUser();

        String login = user.getUsername();
        userService.updateUser(login, firstName, lastName);

        return "redirect:/api/profile";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        List<CustomUser> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable Long id, Model model) {
        CustomUser user = userService.getById(id);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("languages", user.getLanguages());
        return "user";
    }

    @GetMapping("/projects")
    public String getAllProjectsPage(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects"; }

    @GetMapping("/projects/add")
    public String getNewProjectPage() {
        return "newProject";
    }

    @PostMapping("/projects/add")
    public String addProject(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String[] language,
                             @RequestParam Integer teamSize) {
        UserDetails user = getCurrentUser();
        projectService.addProject(name, description, user.getUsername(), language, teamSize);
        return "redirect:/";
    }

    @GetMapping("/project/{id}")
    public String getProjectById(Model model, @PathVariable Long id) {
        Project project = projectService.findById(id);
        model.addAttribute("name", project.getName());
        model.addAttribute("description", project.getDescription());
        model.addAttribute("creator", project.getCreator());
        model.addAttribute("languages", project.getRequiredLanguages());
        model.addAttribute("teamSize", project.getNumberOfTeamMembers());
        model.addAttribute("teamMembers", project.getTeamMembers());
        return "project";
    }


    private UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
