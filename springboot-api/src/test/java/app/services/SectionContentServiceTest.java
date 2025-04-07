package app.application.services;

import app.domain.SectionContent;
import app.infrastructure.SectionContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionContentServiceTest {

    @Mock
    private SectionContentRepository sectionContentRepository;

    @InjectMocks
    private SectionContentService sectionContentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSectionContents() {
        SectionContent sc1 = new SectionContent();
        SectionContent sc2 = new SectionContent();
        when(sectionContentRepository.findAll()).thenReturn(Arrays.asList(sc1, sc2));

        List<SectionContent> result = sectionContentService.getAllSectionContents();

        assertEquals(2, result.size());
        verify(sectionContentRepository, times(1)).findAll();
    }

    @Test
    public void testGetSectionContentById_Found() {
        SectionContent sc = new SectionContent();
        sc.setId(1L);
        when(sectionContentRepository.findById(1L)).thenReturn(Optional.of(sc));

        Optional<SectionContent> result = sectionContentService.getSectionContentById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testGetSectionContentById_NotFound() {
        when(sectionContentRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<SectionContent> result = sectionContentService.getSectionContentById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testAddSectionContent() {
        SectionContent sc = new SectionContent();
        when(sectionContentRepository.save(sc)).thenReturn(sc);

        SectionContent result = sectionContentService.addSectionContent(sc);

        assertNotNull(result);
        verify(sectionContentRepository, times(1)).save(sc);
    }

    @Test
    public void testDeleteSectionContent() {
        sectionContentService.deleteSectionContent(5L);
        verify(sectionContentRepository, times(1)).deleteById(5L);
    }
}