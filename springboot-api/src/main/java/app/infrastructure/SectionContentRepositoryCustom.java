package app.infrastructure;

import java.util.List;

public interface SectionContentRepositoryCustom {
    List<Object[]> searchProfilesWithKeywordsRanked(List<String> keywords, Long executiveSummaryCategoryId);
}
