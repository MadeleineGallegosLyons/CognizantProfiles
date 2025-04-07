package app.controller;

import app.dto.BlobImportRequest;
import app.infrastructure.ProfileImportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileImportController.class)
public class ProfileImportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileImportRepository profileImportRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postImport_withValidBlobName_returns200AndCallsRepo() throws Exception {
        String blobName = "test-profile.json";
        BlobImportRequest request = new BlobImportRequest();
        request.setBlobName(blobName);

        mockMvc.perform(post("/api/profiles/import")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(profileImportRepository, times(1)).importFromBlob(blobName);
    }

    @Test
    void postImport_withEmptyBlobName_returns400() throws Exception {
        BlobImportRequest request = new BlobImportRequest();
        request.setBlobName(""); // or null

        mockMvc.perform(post("/api/profiles/import")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
