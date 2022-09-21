package ua.com.foreach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foreach.model.entity.RootUser;

public interface RootUserRepository extends JpaRepository<RootUser,Long> {
}
