package app.application.services;

import app.domain.Profile;
import app.infrastructure.ProfileRepository;
import app.infrastructure.SectionContentRepository;
import app.dto.ProfileDto;
import app.dto.ProfileSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final SectionContentRepository sectionContentRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    public List<ProfileSearchResultDto> searchProfilesByContent(String query) {
        Long executiveSummaryCategoryId = 4L; // This is the ID for "Executive Summary" based on your schema

        List<Object[]> rawResults = sectionContentRepository.searchProfilesWithSummaryRanked(query, executiveSummaryCategoryId);

        return rawResults.stream().map(row -> {
            ProfileSearchResultDto dto = new ProfileSearchResultDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setName((String) row[1]);
            dto.setSharePointRef((String) row[2]);
            dto.setExecutiveSummary((String) row[3]);
            return dto;
        }).collect(Collectors.toList());
    }
}
