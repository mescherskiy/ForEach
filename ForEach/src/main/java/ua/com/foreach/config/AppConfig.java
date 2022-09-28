package ua.com.foreach.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foreach.model.ProgrammingLanguage;
import ua.com.foreach.repos.LanguageRepository;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner fillLanguages(final LanguageRepository languageRepository) {
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
            }
        };
    }
}
