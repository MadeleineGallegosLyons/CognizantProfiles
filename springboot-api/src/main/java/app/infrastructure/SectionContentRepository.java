package app.infrastructure;

import app.domain.SectionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionContentRepository extends JpaRepository<SectionContent, Long> {

    @Query(value = """
        SELECT p.id, p.name, p.sharepoint_ref,
               (
                   SELECT TOP 1 sc2.section_content
                   FROM section_contents sc2
                   WHERE sc2.profile_id = p.id
                     AND sc2.section_category_id = :executiveSummaryCategoryId
               ) AS executive_summary,
               COUNT(*) AS relevance_score
        FROM profiles p
        JOIN section_contents sc ON p.id = sc.profile_id
        WHERE LOWER(sc.section_content) LIKE LOWER(CONCAT('%', :query, '%'))
        GROUP BY p.id, p.name, p.sharepoint_ref
        ORDER BY relevance_score DESC
        """, nativeQuery = true)

    List<Object[]> searchProfilesWithSummaryRanked(
            @Param("query") String query,
            @Param("executiveSummaryCategoryId") Long executiveSummaryCategoryId
    );
}
