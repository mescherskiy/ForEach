package ua.com.foreach.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.models.ProgrammingLanguage;
import ua.com.foreach.models.Role;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.repos.LanguageRepository;

import java.util.Set;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/templates/**")
                .addResourceLocations("/templates/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");

    }

    @Bean
    public CommandLineRunner fillLanguages(final CustomUserRepository customUserRepository,
                                           final LanguageRepository languageRepository,
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
                customUserRepository.save(new CustomUser("mescherskiy.alexandr@gmail.com",
                        passwordEncoder.encode("123"),
                        "Александр",
                        "Мещерский",
                        Role.USER,
                        Set.of(languageRepository.findByLanguage("Java").get()),
                        false,
                        true));

                customUserRepository.save(new CustomUser("user@gmail.com",
                        passwordEncoder.encode("123"),
                        "Юзер",
                        "Юзеров",
                        Role.USER,
                        Set.of(languageRepository.findByLanguage("HTML").get(), languageRepository.findByLanguage("CSS").get()),
                        false,
                        true));

                customUserRepository.save(new CustomUser("admin@gmail.com",
                        passwordEncoder.encode("123"),
                        "Админ",
                        "Админов",
                        Role.ADMIN,
                        Set.of(languageRepository.findByLanguage("JavaScript").get()),
                        false,
                        true));

            }
        };
    }
}
