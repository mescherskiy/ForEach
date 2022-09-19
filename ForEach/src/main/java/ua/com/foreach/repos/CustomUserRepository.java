package ua.com.foreach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foreach.model.CustomUser;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByEmail(String email);
}
