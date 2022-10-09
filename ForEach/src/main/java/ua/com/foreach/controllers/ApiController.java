package ua.com.foreach.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.UserService;

import java.util.List;


@RestController
@RequestMapping("api/")
public class ApiController {

    private final UserService userService;
    private final ProjectService projectService;

    public ApiController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Object>> search(@RequestParam String pattern) {
        List<Object> list = List.of(userService.findDtoByPattern(pattern, null),
                projectService.findDtoByPattern(pattern, null));
        if(list == null || list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.FOUND);

    }
}
