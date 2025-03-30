package app.infrastructure;

import app.domain.SectionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SectionCategoryRepository extends JpaRepository<SectionCategory, Long> {
    Optional<SectionCategory> findByName(String name);
}
