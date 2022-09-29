package ua.com.foreach.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import ua.com.foreach.repos.LanguageRepository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
