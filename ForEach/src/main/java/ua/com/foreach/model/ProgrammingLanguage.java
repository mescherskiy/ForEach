package ua.com.foreach.model;

import javax.persistence.*;
import java.util.List;

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
}
