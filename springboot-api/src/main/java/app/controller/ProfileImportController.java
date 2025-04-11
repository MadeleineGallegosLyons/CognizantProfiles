package app.controller;

import app.dto.BlobImportRequest;
import app.infrastructure.ProfileImportRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
public class ProfileImportController {

    private final ProfileImportRepository profileImportRepository;

    public ProfileImportController(ProfileImportRepository profileImportRepository) {
        this.profileImportRepository = profileImportRepository;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importProfileFromBlob(@Valid @RequestBody BlobImportRequest request) {
        profileImportRepository.importFromBlob(request.getBlobName());
        return ResponseEntity.ok("Profile import triggered for blob: " + request.getBlobName());
    }
}
