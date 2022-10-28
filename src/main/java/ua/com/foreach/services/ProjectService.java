package ua.com.foreach.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.dto.ProjectDTO;
import ua.com.foreach.models.Apply;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;
import ua.com.foreach.repos.LanguageRepository;
import ua.com.foreach.repos.ProjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CustomUserService customUserService;
    private final LanguageRepository languageRepository;
    private final ApplyService applyService;
    private final EmailService emailService;

    @Transactional
    public Project addProject(String name, String description, String creator, String[] languages) {
        Set<ProgrammingLanguage> langs = new HashSet<>();
        for (String language : languages) {
            langs.add(languageRepository.findByLanguage(language).get());
        }
        Project project = new Project(name, description, creator, langs);
        CustomUser user = customUserService.findByLogin(creator);
        project.getTeamMembers().add(user);
        user.getProjects().add(project);
        projectRepository.save(project);
        return project;
    }

    @Transactional(readOnly = true)
    public Project findByName(String name) {
        return projectRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Project> findByAuthor(String author) {
        CustomUser user = customUserService.findByLogin(author);
        return user.getProjects();
    }

    @Transactional
    public List<Project> findAll() { return projectRepository.findAll(); }

    @Transactional
    public Project findById (Long id) { return projectRepository.findById(id).get(); }

    @Transactional(readOnly = true)
    public List<Project> findByPattern(String pattern, Pageable pageable) {
        return projectRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> findDtoByPattern(String pattern, Pageable pageable) {
        return projectRepository.findByPattern(pattern, pageable).stream().map(Project::toDTO).toList();
    }

    @Transactional
    public void addTeamMember(Project project, CustomUser user) {
        project.getTeamMembers().add(user);
        user.getProjects().add(project);
    }

    @Transactional
    public void acceptApply(Long applyId) {
        Apply apply = applyService.findById(applyId);
        Long projectId = apply.getProject().getId();
        Project project = projectRepository.findById(projectId).get();
        addTeamMember(project, customUserService.findByLogin(apply.getCandidateUsername()));
        String mailCaption = "Apply accepted!";
        String mailText = "Your apply for project \"" + project.getName() + "\" was accepted. Welcome to the team!";
        String mail = emailService.buildEmailWithoutLink(apply.getCandidateFullname(),
                mailText, mailCaption);
        emailService.send(apply.getCandidateUsername(), mail,"Apply accepted");
        applyService.delete(applyId);
    }

    @Transactional
    public void rejectApply(Long applyId) {
        applyService.delete(applyId);
    }

}
