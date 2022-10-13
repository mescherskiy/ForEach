package ua.com.foreach.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.dto.ProjectDTO;
import ua.com.foreach.models.Project;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


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

//    @GetMapping("/add")
//    public void getNewProjectPage(HttpServletResponse response) {
//        try {
//            response.sendRedirect("/api/projects/add");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDTO> add(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String[] language,
                             @RequestParam Integer teamSize) {
        UserDetails user = UserService.getCurrentUser();
        Project project = projectService.addProject(name, description, user.getUsername(), language, teamSize);

        return new ResponseEntity<>(project.toDTO(), HttpStatus.CREATED);
    }


}
