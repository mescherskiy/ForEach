package ua.com.foreach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String telegram;
    private String git;
    private String linkedIn;
    private String facebook;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;
}
