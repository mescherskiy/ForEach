package ua.com.foreach.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.services.CustomUserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    private final CustomUserService customUserService;

    @Autowired
    public UserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = customUserService.getAllDto();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO user = customUserService.getDtoById(id);
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
