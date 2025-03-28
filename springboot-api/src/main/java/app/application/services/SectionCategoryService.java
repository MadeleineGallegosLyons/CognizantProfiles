package app.application.services;

import app.domain.SectionCategory;
import app.infrastructure.SectionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionCategoryService {

    private final SectionCategoryRepository sectionCategoryRepository;

    public List<SectionCategory> getAllSectionCategories() {
        return sectionCategoryRepository.findAll();
    }

    public SectionCategory addSectionCategory(SectionCategory sectionCategory) {
        return sectionCategoryRepository.save(sectionCategory);
    }
}
