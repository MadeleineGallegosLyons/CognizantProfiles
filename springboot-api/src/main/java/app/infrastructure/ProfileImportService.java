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

@Service
public class ProfileImportService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    private final ObjectMapper objectMapper;
    private final ProfileRepository profileRepository;
    private final SectionCategoryRepository sectionCategoryRepository;
    private final SectionContentRepository sectionContentRepository;

    public ProfileImportService(ObjectMapper objectMapper,
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

            // Map and save everything
            Profile profile = mapDtoToEntity(profileDto);
            profileRepository.save(profile);

            if (profileDto.getSections() != null) {
                for (SectionDto sectionDto : profileDto.getSections()) {
                    String sectionName = sectionDto.getSectionName();
                    String content = sectionDto.getSectionContent().toString(); // handles arrays or objects as string

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

    private Profile mapDtoToEntity(ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setName(profileDto.getSharePointRef());  // set as name for now
        profile.setSharePointRef(profileDto.getSharePointRef());
        return profile;
    }
}
