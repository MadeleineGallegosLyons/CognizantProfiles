package app.controller;

import app.services.ProfileService;
import app.dto.ProfileSearchResultDto;
import app.dto.ProfileViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/profile-search")
@RequiredArgsConstructor
public class ProfileSearchController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<ProfileSearchResultDto>> searchProfiles(@RequestParam String query) {
        List<ProfileSearchResultDto> results = profileService.searchProfilesByContent(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileViewDto> getProfileView(@PathVariable Long id) {
        ProfileViewDto profile = profileService.getProfileView(id);
        return ResponseEntity.ok(profile);
    }
}
