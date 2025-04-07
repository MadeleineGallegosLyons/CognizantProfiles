package app.application.services;

import app.domain.*;
import app.dto.ProfileDto;
import app.dto.SectionDto;
import app.infrastructure.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProfileImportServiceTest {

    @Mock
    private AzureBlobService azureBlobService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private SectionCategoryRepository sectionCategoryRepository;

    @Mock
    private SectionContentRepository sectionContentRepository;

    @InjectMocks
    private ProfileImportService profileImportService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileImportService = new ProfileImportService(
                azureBlobService,
                objectMapper,
                profileRepository,
                sectionCategoryRepository,
                sectionContentRepository
        );
    }

    @Test
    void testImportProfileFromBlob() throws Exception {
        // Create a sample ProfileDto
        ProfileDto dto = ProfileDto.builder()
                .sharePointRef("SP123")
                .sections(List.of(
                        SectionDto.builder()
                                .sectionName("Technical Expertise")
                                .sectionContent("Java")
                                .build(),
                        SectionDto.builder()
                                .sectionName("Technical Expertise")
                                .sectionContent("Spring Boot")
                                .build()
                ))
                .build();

        // Serialize it to JSON and wrap it in an InputStream
        String json = objectMapper.writeValueAsString(dto);
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());

        when(azureBlobService.getProfileJson("test.json")).thenReturn(mockInputStream);

        // Mock DB behavior
        Profile savedProfile = Profile.builder().id(1L).sharePointRef("SP123").build();
        when(profileRepository.save(any())).thenReturn(savedProfile);

        when(sectionCategoryRepository.findByName("Technical Expertise"))
                .thenReturn(Optional.empty());

        when(sectionCategoryRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        profileImportService.importProfileFromBlob("test.json");

        // Assert
        verify(profileRepository, times(1)).save(any(Profile.class));
        verify(sectionCategoryRepository, times(2)).findByName("Technical Expertise");
        verify(sectionCategoryRepository, atLeastOnce()).save(any(SectionCategory.class));
        verify(sectionContentRepository, times(2)).save(any(SectionContent.class));
    }
}
