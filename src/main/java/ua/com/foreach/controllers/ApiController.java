package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.services.ProjectService;
import ua.com.foreach.services.UserService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class ApiController {

    private final UserService userService;
    private final ProjectService projectService;


    @PostMapping("/search")
    public ResponseEntity<List<Object>> search(@RequestParam String pattern) {
        List<Object> list = List.of(userService.findDtoByPattern(pattern, null),
                projectService.findDtoByPattern(pattern, null));
        if(list == null || list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
}
