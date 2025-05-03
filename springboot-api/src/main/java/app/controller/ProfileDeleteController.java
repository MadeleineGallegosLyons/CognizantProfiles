package app.controller;

import app.application.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile-delete")
@RequiredArgsConstructor
public class ProfileDeleteController {

    private final ProfileService profileService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        boolean deleted = profileService.deleteProfileById(id);
        if (deleted) {
            return ResponseEntity.ok("Profile with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
