package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.dto.ProjectDTO;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.Project;
import ua.com.foreach.services.ApplyService;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.CustomUserService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final CustomUserService customUserService;
    private final ApplyService applyService;

    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getAll() {
        List<ProjectDTO> projects = projectService.findAll().stream().map(Project::toDTO).toList();
        if(projects.isEmpty())
            return new ResponseEntity<>(projects, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(projects, HttpStatus.OK); }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        ProjectDTO projectDTO = projectService.findById(id).toDTO();
        if(projectDTO == null)
            return new ResponseEntity<>(projectDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDTO> add(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String[] language) {
        UserDetails user = CustomUserService.getCurrentUser();
        Project project = projectService.addProject(name, description, user.getUsername(),
                language);

        return new ResponseEntity<>(project.toDTO(), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity join(@PathVariable Long id) {
        Project project = projectService.findById(id);
        CustomUser user = customUserService.findByLogin(CustomUserService.getCurrentUser().getUsername());
        applyService.add(project, user);
        return ResponseEntity.ok().build();
    }

}
