package ua.com.foreach.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foreach.model.entity.CustomUser;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/customer_users")
public class RestController {

    @GetMapping
    public List<CustomUser> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public CustomUser getById(@PathVariable Long id) {
        return null;
    }
}
