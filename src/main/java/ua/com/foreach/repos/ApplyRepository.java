package ua.com.foreach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foreach.models.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
