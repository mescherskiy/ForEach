package ua.com.foreach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foreach.models.ProgrammingLanguage;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {
    Optional<ProgrammingLanguage> findByLanguage(String language);
}