package app.controller;

import app.controller.ProfileSearchController;
import app.application.services.ProfileService;
import app.dto.ProfileSearchResultDto;
import app.dto.ProfileViewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileSearchController.class)
public class ProfileSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void searchProfiles_withValidQuery_returnsResults() throws Exception {
        List<ProfileSearchResultDto> mockResults = List.of(new ProfileSearchResultDto());

        when(profileService.searchProfilesByKeywords(List.of("java", "sql")))
                .thenReturn(mockResults);

        mockMvc.perform(get("/api/profile-search")
                        .param("query", "java,sql")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(profileService).searchProfilesByKeywords(List.of("java", "sql"));
    }

    @Test
    void getProfileView_withValidId_returnsProfileDetails() throws Exception {
        Long profileId = 1L;
        ProfileViewDto mockView = new ProfileViewDto();
        mockView.setId(profileId);

        when(profileService.getProfileView(profileId)).thenReturn(mockView);

        mockMvc.perform(get("/api/profile-search/{id}", profileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profileId));

        verify(profileService).getProfileView(profileId);
    }
}
