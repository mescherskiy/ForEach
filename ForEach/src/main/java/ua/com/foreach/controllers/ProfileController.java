package ua.com.foreach.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getProfile() {
        UserDTO user = userService.findByLogin(UserService.getCurrentUser().getUsername()).toDTO();
        if(user == null)
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<UserDTO> getUpdatePage() {
        return getProfile();
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestParam(required = false, name = "firstname") String firstName,
                                          @RequestParam(required = false, name = "lastname") String lastName) {
        String email = UserService.getCurrentUser().getUsername();
        if(email == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDTO updatedUser = userService.updateUser(firstName, lastName, email);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
