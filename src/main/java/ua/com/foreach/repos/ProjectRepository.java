package ua.com.foreach.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.foreach.dto.ProjectDTO;
import ua.com.foreach.models.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);

    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pattern, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Project> findByPattern(@Param("pattern") String pattern,
                                   Pageable pageable);
}
