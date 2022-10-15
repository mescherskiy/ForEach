package ua.com.foreach.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.models.CustomUser;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByLogin(String login);


    @Transactional
    @Modifying
    @Query("UPDATE CustomUser a " +
            "SET a.enabled = TRUE WHERE a.login = ?1")
    int enableCustomUser(String login);

    @Transactional
    @Modifying
    @Query("UPDATE CustomUser a " +
            "SET a.password = ?1 WHERE a.id = ?2")
    int updateUserPassword(String newPassword, Long user_id);

    @Transactional
    @Modifying
    @Query("UPDATE CustomUser a " +
            "SET a.fullName = ?1 WHERE a.login = ?2")
    void updateUser(String fullName, String login);


    @Query("SELECT u FROM CustomUser u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :pattern, '%')) " +
            "OR LOWER(u.login) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<CustomUser> findByPattern(@Param("pattern") String pattern,
                                Pageable pageable);

}
