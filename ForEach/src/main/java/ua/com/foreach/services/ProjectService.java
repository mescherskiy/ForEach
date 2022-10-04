package ua.com.foreach.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;
import ua.com.foreach.repos.LanguageRepository;
import ua.com.foreach.repos.ProjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final LanguageRepository languageRepository;

    public ProjectService(ProjectRepository projectRepository, UserService userService, LanguageRepository languageRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.languageRepository = languageRepository;
    }

    @Transactional
    public void addProject(String name, String description, String creator, String[] languages, Integer teamSize) {
        Set<ProgrammingLanguage> langs = new HashSet<>();
        for (String language : languages) {
            langs.add(languageRepository.findByLanguage(language).get());
        }
        Project project = new Project(name, description, creator, langs, teamSize);
        CustomUser user = userService.findByLogin(creator);
        project.getTeamMembers().add(user);
        user.getProjects().add(project);
        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Project findByName(String name) {
        return projectRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Project> findByCreator(String creator) {
        CustomUser user = userService.findByLogin(creator);
        return user.getProjects();
    }

    @Transactional
    public List<Project> findAll() { return projectRepository.findAll(); }

    @Transactional
    public Project findById (Long id) { return projectRepository.findById(id).orElse(null); }

}
