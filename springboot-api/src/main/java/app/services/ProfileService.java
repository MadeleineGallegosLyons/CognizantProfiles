package app.services;

import app.domain.Profile;
import app.domain.SectionContent;
//import app.dto.ProfileDto;
import app.dto.ProfileSearchResultDto;
import app.dto.ProfileViewDto;
import app.dto.SectionDto;
import app.infrastructure.ProfileRepository;
import app.infrastructure.SectionContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
        Long executiveSummaryCategoryId = 4L; // ID for "Executive Summary"

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

    public ProfileViewDto getProfileView(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<SectionContent> sectionContents = sectionContentRepository.findByProfileId(profileId);

        Map<String, List<String>> groupedSections = sectionContents.stream()
                .collect(Collectors.groupingBy(
                        sc -> sc.getSectionCategory().getName(),
                        Collectors.mapping(SectionContent::getSectionContent, Collectors.toList())
                ));

        // Extract Email and Job Title (if available), then remove them
        String email = Optional.ofNullable(groupedSections.remove("Email"))
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);

        String jobTitle = Optional.ofNullable(groupedSections.remove("Job Title"))
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);

        List<String> sectionOrder = List.of(
                "Executive Summary",
                "Functional Expertise",
                "Technical Expertise",
                "Industry Sectors",
                "Methodologies",
                "Experience",
                "Languages Spoken",
                "Certifications",
                "Mobility"
        );

        List<SectionDto> sectionDtos = groupedSections.entrySet().stream()
                .filter(entry -> !entry.getKey().equalsIgnoreCase("Name")) // remove "Name"
                .sorted(Comparator.comparingInt(entry ->
                        sectionOrder.indexOf(entry.getKey()) == -1 ? Integer.MAX_VALUE : sectionOrder.indexOf(entry.getKey())
                ))
                .map(entry -> new SectionDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        ProfileViewDto dto = new ProfileViewDto();
        dto.setId(profile.getId());
        dto.setName(profile.getName());
        dto.setSharePointRef(profile.getSharePointRef());
        dto.setEmail(email);
        dto.setJobTitle(jobTitle);
        dto.setSections(sectionDtos);

        return dto;
    }
}
