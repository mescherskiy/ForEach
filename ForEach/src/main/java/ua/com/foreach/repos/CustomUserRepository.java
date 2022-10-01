package ua.com.foreach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.models.CustomUser;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE CustomUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableCustomUser(String email);

    @Query("SELECT u FROM CustomUser u where u.email = :login")
    CustomUser findByLogin(@Param("login") String login);
}
