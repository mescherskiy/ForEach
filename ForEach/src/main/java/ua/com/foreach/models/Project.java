package ua.com.foreach.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_languages",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<ProgrammingLanguage> requiredLanguages = new HashSet<>();

    private Integer numberOfTeamMembers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<CustomUser> teamMembers = new HashSet<>();

//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
//
//    public Set<ProgrammingLanguage> getRequiredLanguages() {
//        return requiredLanguages;
//    }
//
//    public void setRequiredLanguages(Set<ProgrammingLanguage> requiredLanguages) {
//        this.requiredLanguages = requiredLanguages;
//    }
//
//    public Integer getNumberOfTeamMembers() {
//        return numberOfTeamMembers;
//    }
//
//    public void setNumberOfTeamMembers(Integer numberOfTeamMembers) {
//        this.numberOfTeamMembers = numberOfTeamMembers;
//    }
//
//    public Set<CustomUser> getTeamMembers() {
//        return teamMembers;
//    }
//
//    public void setTeamMembers(Set<CustomUser> teamMembers) {
//        this.teamMembers = teamMembers;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Project project = (Project) o;
//        return name.equals(project.name) && Objects.equals(description, project.description) && numberOfTeamMembers.equals(project.numberOfTeamMembers);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, description, numberOfTeamMembers);
//    }

    public Project(String name, String description, String creator, Set<ProgrammingLanguage> requiredLanguages, Integer numberOfTeamMembers) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.requiredLanguages = requiredLanguages;
        this.numberOfTeamMembers = numberOfTeamMembers;
    }
}
