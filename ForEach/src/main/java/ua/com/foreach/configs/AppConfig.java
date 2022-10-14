package ua.com.foreach.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Project;
import ua.com.foreach.models.Role;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.repos.LanguageRepository;
import ua.com.foreach.repos.ProjectRepository;

import java.util.List;
import java.util.Set;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public CommandLineRunner fillLanguages(final CustomUserRepository customUserRepository,
                                           final LanguageRepository languageRepository,
                                           final ProjectRepository projectRepository,
                                           final BCryptPasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                languageRepository.saveAll(Set.of(
                        new ProgrammingLanguage("Java"),
                        new ProgrammingLanguage("Python"),
                        new ProgrammingLanguage("C++"),
                        new ProgrammingLanguage("C#"),
                        new ProgrammingLanguage("Go"),
                        new ProgrammingLanguage("Ruby"),
                        new ProgrammingLanguage("JavaScript"),
                        new ProgrammingLanguage("TypeScript"),
                        new ProgrammingLanguage("HTML"),
                        new ProgrammingLanguage("CSS"),
                        new ProgrammingLanguage("PHP")));

                Project project = new Project("Super app",
                        "This application will make happier every person in the world, except russians",
                        "mescherskiy.alexandr@gmail.com",
                        Set.of(languageRepository.findByLanguage("Java").get()));
                projectRepository.save(project);

                CustomUser user = new CustomUser("mescherskiy.alexandr@gmail.com",
                        passwordEncoder.encode("123"),
                        "Александр Мещерский",
                        Role.USER,
                        Set.of(languageRepository.findByLanguage("Java").get()),
                        List.of(project),
                        false,
                        true);

                customUserRepository.save(user);

                project.getTeamMembers().add(user);
                projectRepository.save(project);

                customUserRepository.save(new CustomUser("user@gmail.com",
                        passwordEncoder.encode("123"),
                        "Юзер Юзеров",
                        Role.USER,
                        Set.of(languageRepository.findByLanguage("HTML").get(), languageRepository.findByLanguage("CSS").get()),
                        false,
                        true));

                customUserRepository.save(new CustomUser("admin@gmail.com",
                        passwordEncoder.encode("123"),
                        "Админ Админов",
                        Role.ADMIN,
                        Set.of(languageRepository.findByLanguage("JavaScript").get()),
                        false,
                        true));

            }
        };
    }
}
