package ua.com.foreach.services;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.dto.ApplyDTO;
import ua.com.foreach.models.Apply;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;
import ua.com.foreach.repos.ApplyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final EmailService emailService;
    private final UserService userService;

    @Transactional
    public void save(Apply apply) { applyRepository.save(apply); }

    @Transactional(readOnly = true)
    public List<ApplyDTO> findAll() { return applyRepository.findAll().stream().map(Apply::toDTO).toList(); }

    @Transactional(readOnly = true)
    public Apply findById(Long id) {return applyRepository.findById(id).get(); }

    @Transactional
    public void add(Project project, CustomUser user) {
        String languages = "";
        for (ProgrammingLanguage lang : user.getLanguages()) {
            languages+= lang.getLanguage() + ", ";
        }
        languages = languages.substring(0, languages.length() - 2);

        Apply apply = new Apply(user.getFullName(), user.getUsername(), languages, project);
        project.getApplies().add(apply);
        String link = "http://localhost:8080/api/profile/projects/applies";
        String mailCaption = "New apply incoming";
        String mailText = "You have a new apply. " + apply.getCandidateFullname() +
                " wants to join your project \"" + project.getName() + "\". Click below for more details:";
        String mail = emailService.buildEmailWithLink(userService.findByLogin(project.getAuthor()).getFullName(),
                mailText, mailCaption, link);
        emailService.send(project.getAuthor(), mail,"New apply");
    }

    @Transactional
    public void delete(Long id) {
        applyRepository.deleteById(id);
    }
}
