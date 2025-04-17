package app.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SectionContentRepositoryImpl implements SectionContentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchProfilesWithKeywordsRanked(List<String> keywords, Long executiveSummaryCategoryId) {
        // Build the WHERE clause with multiple LIKE conditions
        StringBuilder whereClause = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            if (i > 0) whereClause.append(" OR ");
            whereClause.append("LOWER(sc.section_content) LIKE LOWER(CONCAT('%', :kw").append(i).append(", '%'))");
        }

        String sql =
                "SELECT p.id, p.name, p.sharepoint_ref, " +
                        "       ( " +
                        "           SELECT TOP 1 sc2.section_content " +
                        "           FROM section_contents sc2 " +
                        "           WHERE sc2.profile_id = p.id " +
                        "             AND sc2.section_category_id = :executiveSummaryCategoryId " +
                        "       ) AS executive_summary, " +
                        "       COUNT(*) AS relevance_score " +
                        "FROM profiles p " +
                        "JOIN section_contents sc ON p.id = sc.profile_id " +
                        "WHERE " + whereClause +
                        " GROUP BY p.id, p.name, p.sharepoint_ref " +
                        "ORDER BY relevance_score DESC";

        Query query = entityManager.createNativeQuery(sql);

        // Set keyword parameters
        for (int i = 0; i < keywords.size(); i++) {
            query.setParameter("kw" + i, keywords.get(i));
        }

        query.setParameter("executiveSummaryCategoryId", executiveSummaryCategoryId);

        return query.getResultList();
    }
}
