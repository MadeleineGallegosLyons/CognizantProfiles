package app.controllers;

import app.application.services.ProfileService;
import app.dto.ProfileSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
