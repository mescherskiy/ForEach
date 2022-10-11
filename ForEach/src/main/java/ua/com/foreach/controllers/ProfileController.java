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


    @PostMapping("/update")
    public ResponseEntity<UserDTO> update(HttpServletResponse response, @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName) {
        UserDTO user = userService.findDtoByLogin(UserService.getCurrentUser().getUsername());
        if(user == null)
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        UserDTO updatedUser = userService.updateUser(user.getEmail(), firstName, lastName).toDTO();

//        try {
//            response.sendRedirect("api/profile");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
