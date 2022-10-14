package ua.com.foreach.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.dto.ProjectDTO;
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
    public Project addProject(String name, String description, String creator, String[] languages) {
        Set<ProgrammingLanguage> langs = new HashSet<>();
        for (String language : languages) {
            langs.add(languageRepository.findByLanguage(language).get());
        }
        Project project = new Project(name, description, creator, langs);
        CustomUser user = userService.findByLogin(creator);
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
        CustomUser user = userService.findByLogin(author);
        return user.getProjects();
    }

    @Transactional
    public List<Project> findAll() { return projectRepository.findAll(); }

    @Transactional
    public Project findById (Long id) { return projectRepository.findById(id).orElse(null); }

    @Transactional(readOnly = true)
    public List<Project> findByPattern(String pattern, Pageable pageable) {
        return projectRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> findDtoByPattern(String pattern, Pageable pageable) {
        return projectRepository.findByPattern(pattern, pageable).stream().map(Project::toDTO).toList();
    }


}
