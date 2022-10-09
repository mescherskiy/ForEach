package ua.com.foreach.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.foreach.dto.ProjectDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_languages",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<ProgrammingLanguage> requiredLanguages = new HashSet<>();

    private Integer numberOfTeamMembers;

    @ManyToMany(mappedBy = "projects")
    private Set<CustomUser> teamMembers = new HashSet<>();

    public Project(String name, String description, String author, Set<ProgrammingLanguage> requiredLanguages, Integer numberOfTeamMembers) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.requiredLanguages = requiredLanguages;
        this.numberOfTeamMembers = numberOfTeamMembers;
    }

    public ProjectDTO toDTO() {
        return ProjectDTO.of(this.name, this.description, this.author, this.requiredLanguages,
                this.numberOfTeamMembers, this.teamMembers);
    }
}
