package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.CustomUserService;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("api/")
public class ApiController {

    private final CustomUserService customUserService;
    private final ProjectService projectService;


    @PostMapping("/search")
    public ResponseEntity<List<Object>> search(@RequestParam String pattern) {
        List<Object> list = List.of(customUserService.findDtoByPattern(pattern, null),
                projectService.findDtoByPattern(pattern, null));
        if(list == null || list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
}
