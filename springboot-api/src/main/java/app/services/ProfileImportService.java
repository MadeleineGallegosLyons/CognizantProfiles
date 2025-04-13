package app.services;

import app.dto.ProfileDto;
import app.dto.SectionDto;
import app.domain.*;
import app.infrastructure.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileImportService {

    private final AzureBlobService azureBlobService;
    private final ObjectMapper objectMapper;
    private final ProfileRepository profileRepository;
    private final SectionCategoryRepository sectionCategoryRepository;
    private final SectionContentRepository sectionContentRepository;

    public void importProfileFromBlob(String blobName) throws Exception {
        InputStream stream = azureBlobService.getProfileJson(blobName);
        ProfileDto dto = objectMapper.readValue(stream, ProfileDto.class);

        Profile profile = Profile.builder()
                .sharePointRef(dto.getSharePointRef())
                .build();
        profile = profileRepository.save(profile);

        for (SectionDto section : dto.getSections()) {
            String categoryName = section.getSectionName();
            SectionCategory category = sectionCategoryRepository
                    .findByName(categoryName)
                    .orElseGet(() -> sectionCategoryRepository.save(
                            SectionCategory.builder().name(categoryName).build()
                    ));

            List<String> contents = normalize(section.getSectionContent());
            for (String content : contents) {
                SectionContent sc = SectionContent.builder()
                        .profile(profile)
                        .sectionCategory(category)
                        .sectionContent(content)
                        .build();
                sectionContentRepository.save(sc);
            }
        }
    }

    private List<String> normalize(Object sectionContent) {
        if (sectionContent instanceof String) {
            return List.of((String) sectionContent);
        } else if (sectionContent instanceof List) {
            return (List<String>) sectionContent;
        } else if (sectionContent instanceof Map) {
            return List.of(new ObjectMapper().convertValue(sectionContent, String.class));
        }
        return List.of("[Unsupported Content Type]");
    }
}