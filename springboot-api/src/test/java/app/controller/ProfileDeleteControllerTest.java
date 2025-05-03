package app.controller;

import app.application.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileDeleteController.class)
public class ProfileDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void deleteProfile_withExistingId_returns200AndCallsService() throws Exception {
        Long testId = 1L;
        when(profileService.deleteProfileById(testId)).thenReturn(true);

        mockMvc.perform(delete("/api/profile-delete/{id}", testId))
                .andExpect(status().isOk());

        verify(profileService).deleteProfileById(testId);
    }

    @Test
    void deleteProfile_withNonexistentId_returns404() throws Exception {
        Long testId = 999L;
        when(profileService.deleteProfileById(testId)).thenReturn(false);

        mockMvc.perform(delete("/api/profile-delete/{id}", testId))
                .andExpect(status().isNotFound());
    }
}
