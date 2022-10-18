package ua.com.foreach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.com.foreach.models.Contacts;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDTO {
    private String login;
    private String fullName;
    private Contacts contacts;
    private List<String> languages;
    private List<String> projects;
}
