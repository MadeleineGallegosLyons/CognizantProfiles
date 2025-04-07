package app.dto;

import jakarta.validation.constraints.NotBlank;

public class BlobImportRequest {

    @NotBlank(message = "Blob name is required")
    private String blobName;

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }
}
