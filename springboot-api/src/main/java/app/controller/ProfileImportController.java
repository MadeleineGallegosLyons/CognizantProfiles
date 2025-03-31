package app.controller;

import app.dto.BlobImportRequest;
import app.infrastructure.ProfileImportService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/profiles")
public class ProfileImportController {

    private final ProfileImportService profileImportService;

    public ProfileImportController(ProfileImportService profileImportService) {
        this.profileImportService = profileImportService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importProfileFromBlob(@RequestBody BlobImportRequest request) {
        profileImportService.importFromBlob(request.getBlobName());
        return ResponseEntity.ok("Profile import triggered for blob: " + request.getBlobName());
    }
}
