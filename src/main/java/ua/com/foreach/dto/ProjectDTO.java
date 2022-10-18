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
    private Boolean isOpen;
    private Set<String> teamMembers;

    public static ProjectDTO of(String name, String description, String author, Set<ProgrammingLanguage> languages,
                                Boolean isOpen, Set<CustomUser> teamMembers) {
        return new ProjectDTO(name, description, author,
                languages.stream().map(ProgrammingLanguage::getLanguage).collect(Collectors.toSet()),
                isOpen,
                teamMembers.stream().map(CustomUser::getLogin).collect(Collectors.toSet()));
    }
}
