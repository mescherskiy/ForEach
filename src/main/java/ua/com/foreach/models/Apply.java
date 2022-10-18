package ua.com.foreach.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.foreach.dto.ApplyDTO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applies")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String candidateFullname;
    private String candidateUsername;
    private String languages;
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    public Apply(String candidateFullname, String candidateUsername, String languages, Project project) {
        this.candidateFullname = candidateFullname;
        this.candidateUsername = candidateUsername;
        this.languages = languages;
        this.project = project;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "candidateFullname='" + candidateFullname + '\'' +
                ", candidateUsername='" + candidateUsername + '\'' +
                ", languages='" + languages + '\'' +
                '}';
    }

    public ApplyDTO toDTO() {
        return ApplyDTO.of (this.project.getName(), this.candidateFullname, this.candidateUsername, this.languages);
    }
}
