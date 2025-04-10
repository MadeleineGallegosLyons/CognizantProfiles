package app.infrastructure;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.domain.Profile;
import app.domain.SectionCategory;
import app.domain.SectionContent;
import app.dto.ProfileDto;
import app.dto.SectionDto;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfileImportRepository {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    private final ObjectMapper objectMapper;
    private final ProfileRepository profileRepository;
    private final SectionCategoryRepository sectionCategoryRepository;
    private final SectionContentRepository sectionContentRepository;

    public ProfileImportRepository(ObjectMapper objectMapper,
                                   ProfileRepository profileRepository,
                                   SectionCategoryRepository sectionCategoryRepository,
                                   SectionContentRepository sectionContentRepository) {
        this.objectMapper = objectMapper;
        this.profileRepository = profileRepository;
        this.sectionCategoryRepository = sectionCategoryRepository;
        this.sectionContentRepository = sectionContentRepository;
    }

    public void importFromBlob(String blobName) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            if (!blobClient.exists()) {
                throw new FileNotFoundException("Blob not found: " + blobName);
            }

            InputStream blobStream = blobClient.openInputStream();
            ProfileDto profileDto = objectMapper.readValue(blobStream, ProfileDto.class);

            // Extract name from "Name" section
            String extractedName = profileDto.getSections().stream()
                    .filter(s -> "Name".equalsIgnoreCase(s.getSectionName()))
                    .map(SectionDto::getSectionContent)
                    .filter(String.class::isInstance)
                    .map(Object::toString)
                    .findFirst()
                    .orElse(null);

            Profile profile = new Profile();
            profile.setName(extractedName);
            profile.setSharePointRef(profileDto.getSharePointRef());
            profileRepository.save(profile);

            if (profileDto.getSections() != null) {
                for (SectionDto sectionDto : profileDto.getSections()) {
                    String sectionName = sectionDto.getSectionName();
                    String content = formatSectionContent(sectionDto.getSectionContent());

                    if (content == null || content.trim().isEmpty()) {
                        continue; // skip saving blank entries
                    }

                    SectionCategory category = sectionCategoryRepository
                            .findByName(sectionName)
                            .orElseGet(() -> sectionCategoryRepository.save(
                                    SectionCategory.builder().name(sectionName).build()
                            ));

                    SectionContent sectionContent = SectionContent.builder()
                            .profile(profile)
                            .sectionCategory(category)
                            .sectionContent(content)
                            .build();

                    sectionContentRepository.save(sectionContent);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to import profile from blob", e);
        }
    }

    private String formatSectionContent(Object contentObj) {
        if (contentObj instanceof Map<?, ?> map) {
            // Handle complex Experience entries
            if (map.containsKey("project_header") && map.containsKey("project_details")) {
                Map<String, Object> header = (Map<String, Object>) map.get("project_header");
                List<?> details = (List<?>) map.get("project_details");

                String headerStr = String.format("%s - %s - %s",
                        header.getOrDefault("project_title", ""),
                        header.getOrDefault("project_position", ""),
                        header.getOrDefault("project_industry", ""));

                String detailsStr = details.stream()
                        .filter(item -> item instanceof String && !((String) item).equals("•"))
                        .map(Object::toString)
                        .collect(Collectors.joining(" - "));

                return headerStr + " - " + detailsStr;
            }
            return map.toString(); // fallback
        } else if (contentObj instanceof List<?> list) {
            return list.stream()
                    .filter(item -> item instanceof String && !((String) item).equals("•"))
                    .map(Object::toString)
                    .collect(Collectors.joining(" - "));
        } else if (contentObj instanceof String s) {
            return s.equals("•") ? "" : s;
        } else {
            return contentObj.toString();
        }
    }
}
