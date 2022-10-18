package ua.com.foreach.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foreach.dto.ProjectDTO;

import javax.persistence.*;
import java.util.*;

@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    private Boolean isOpen = true;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.REFRESH)
    private Set<CustomUser> teamMembers = new HashSet<>();


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();


    public Project(String name, String description, String author, Set<ProgrammingLanguage> requiredLanguages) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.requiredLanguages = requiredLanguages;
    }

    public ProjectDTO toDTO() {
        return ProjectDTO.of(this.name, this.description, this.author,
                this.requiredLanguages,this.isOpen,
                this.teamMembers);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
