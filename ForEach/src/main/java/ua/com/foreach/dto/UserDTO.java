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
    private String email;
    private String fullName;
    private Contacts contacts;
    private List<String> languages;
    private List<String> projects;

//    public UserDTO of(String email, String fullName, Set<ProgrammingLanguage> languages, List<Project> projects) {
//        return new UserDTO(email, fullName, contacts,
//                languages.stream().map(ProgrammingLanguage::getLanguage).toList(),
//                projects.stream().map(Project::getName).toList());
//    }
}
