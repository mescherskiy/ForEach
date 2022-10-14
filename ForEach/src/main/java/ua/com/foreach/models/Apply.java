package ua.com.foreach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String candidateName;
    private String languages;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Project project;

    public Apply(String candidateName, String languages, Project project) {
        this.candidateName = candidateName;
        this.languages = languages;
        this.project = project;
    }
}
