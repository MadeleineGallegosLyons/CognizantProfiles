package app.controller;

import app.application.services.ProfileService;
import app.dto.ProfileSearchResultDto;
import app.dto.ProfileViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profile-search")
@RequiredArgsConstructor
public class ProfileSearchController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<ProfileSearchResultDto>> searchProfiles(@RequestParam String query) {
        List<String> keywords = Arrays.stream(query.split(","))
                .map(String::trim)
                .filter(k -> !k.isEmpty())
                .toList();

        List<ProfileSearchResultDto> results = profileService.searchProfilesByKeywords(keywords);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileViewDto> getProfileView(@PathVariable Long id) {
        ProfileViewDto profile = profileService.getProfileView(id);
        return ResponseEntity.ok(profile);
    }
}
