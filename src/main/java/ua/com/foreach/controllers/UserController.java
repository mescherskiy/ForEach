package ua.com.foreach.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAllDto();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO user = userService.getDtoById(id);
        if(user == null)
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping
//    @PreAuthorize("hasAuthority('users:write')")
//    public void create(@RequestBody CustomUser user) {
//        customUserRepository.save(user);
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('users:write')")
//    public void deleteById(@PathVariable Long id) {
//        customUserRepository.deleteById(id);
//    }
}
