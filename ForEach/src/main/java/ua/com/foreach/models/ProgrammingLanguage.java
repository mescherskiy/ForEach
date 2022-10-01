package ua.com.foreach.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "languages")
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language")
    private String language;

    @ManyToMany(mappedBy = "languages")
    private List<CustomUser> users;

    public ProgrammingLanguage() {
    }

    public ProgrammingLanguage(String language) {
        this.language = language;
    }

}
