package ua.com.foreach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private String name;
    private String description;
    private String author;
    private Set<String> requiredLanguages;
    private Integer numberOfTeamMembers;
    private Set<String> teamMembers;

    public static ProjectDTO of(String name, String description, String author, Set<ProgrammingLanguage> languages,
                                Integer numberOfTeamMembers, Set<CustomUser> teamMembers) {
        return new ProjectDTO(name, description, author,
                languages.stream().map(ProgrammingLanguage::getLanguage).collect(Collectors.toSet()),
                numberOfTeamMembers,
                teamMembers.stream().map(CustomUser::getEmail).collect(Collectors.toSet()));
    }
}
