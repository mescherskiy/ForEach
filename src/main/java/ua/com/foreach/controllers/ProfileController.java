package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.dto.ApplyDTO;
import ua.com.foreach.dto.ProjectDTO;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.models.Apply;
import ua.com.foreach.models.Project;
import ua.com.foreach.services.ApplyService;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/profile")
public class ProfileController {
    private final UserService userService;
    private final ApplyService applyService;
    private final ProjectService projectService;


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
    public ResponseEntity<UserDTO> update(@RequestParam(required = false, name = "fullname") String fullName) {
        String login = UserService.getCurrentUser().getUsername();
        if(login == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDTO updatedUser = userService.updateUser(fullName, login);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        return new ResponseEntity<>(userService.getProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/applies")
    public ResponseEntity<List<ApplyDTO>> getApplies () {
        return new ResponseEntity<> (applyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/projects/applies/{id}")
    public ResponseEntity<ApplyDTO> getApplyById (@PathVariable Long id) {
        return new ResponseEntity<>(applyService.findById(id).toDTO(), HttpStatus.OK);
    }

    @PostMapping("/projects/applies/{id}/accept")
    public void acceptApply (@PathVariable Long id) {
        projectService.acceptApply(id);
    }

    @PostMapping("/projects/applies/{id}/reject")
    public void rejectApply (@PathVariable Long id) {
        projectService.rejectApply(id);
    }

}
