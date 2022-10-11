package ua.com.foreach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> languages;
    private List<String> projects;

    public static UserDTO of (String email, String firstName, String lastName, Set<ProgrammingLanguage> languages, List<Project> projects) {
        return new UserDTO(email, firstName, lastName,
                languages.stream().map(ProgrammingLanguage::getLanguage).toList(),
                projects.stream().map(Project::getName).toList());
    }
}
