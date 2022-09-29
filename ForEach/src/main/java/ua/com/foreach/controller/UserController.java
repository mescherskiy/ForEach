package ua.com.foreach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foreach.model.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public UserController(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @GetMapping
    public List<CustomUser> getAll() {
        return customUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public CustomUser getById(@PathVariable Long id) {
        return customUserRepository.findById(id).orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:write')")
    public void create(@RequestBody CustomUser user) {
        customUserRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public void deleteById(@PathVariable Long id) {
        customUserRepository.deleteById(id);
    }
}
