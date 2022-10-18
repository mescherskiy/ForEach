package ua.com.foreach.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.foreach.dto.UserDTO;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "users")
public class CustomUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_languages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<ProgrammingLanguage> languages;

    @Column(name = "locked")
    private Boolean locked = false;
    @Column(name = "enabled")
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects = new ArrayList<>();

    private String avatarFileName = "default.jpg";


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contacts_id")
    private Contacts contacts = new Contacts();

    public CustomUser(String login, String password, String fullName, Role role, Set<ProgrammingLanguage> languages,
                      Boolean locked, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.languages = languages;
        this.locked = locked;
        this.enabled = enabled;
        contacts.setEmail(login);
    }

    public CustomUser(String login, String password, String fullName, Role role, Set<ProgrammingLanguage> languages, List<Project> projects, Boolean locked, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.languages = languages;
        this.projects = projects;
        this.locked = locked;
        this.enabled = enabled;
        contacts.setEmail(login);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UserDTO toDTO () {
        return new UserDTO(
                this.getLogin(),
                this.getFullName(),
                this.getContacts(),
                this.getLanguages().stream().map(ProgrammingLanguage::getLanguage).toList(),
                this.getProjects().stream().map(Project::getName).toList());
    }

    @Override
    public String toString() {
        return "CustomUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", contacts=" + contacts +
                '}';
    }
}
