package app.application.services;

import app.domain.Profile;
import app.domain.SectionCategory;
import app.domain.SectionContent;
import app.infrastructure.SectionContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionContentService {

    private final SectionContentRepository sectionContentRepository;

    public List<SectionContent> getAllSectionContents() {
        return sectionContentRepository.findAll();
    }

    public Optional<SectionContent> getSectionContentById(Long id) {
        return sectionContentRepository.findById(id);
    }

    public SectionContent addSectionContent(SectionContent sectionContent) {
        return sectionContentRepository.save(sectionContent);
    }

    public void deleteSectionContent(Long id) {
        sectionContentRepository.deleteById(id);
    }
}