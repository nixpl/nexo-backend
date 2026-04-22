package pl.edu.uj.tp.nexo.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.uj.tp.nexo.organization.dto.OrganizationResponse;
import pl.edu.uj.tp.nexo.organization.dto.UpdateOrganizationRequest;
import pl.edu.uj.tp.nexo.organization.service.OrganizationService;
import pl.edu.uj.tp.nexo.security.config.SecurityConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
@Import(SecurityConfig.class)
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateOrganization_whenOrganizationExists_shouldReturnUpdatedOrganization() throws Exception {
        long organizationId = 1L;
        UpdateOrganizationRequest updateOrganizationRequest = UpdateOrganizationRequest.builder()
                .name("New Name")
                .build();

        OrganizationResponse organizationResponse = OrganizationResponse.builder()
                .id(organizationId)
                .name("New Name")
                .build();

        when(organizationService.updateOrganization(eq(organizationId), any(UpdateOrganizationRequest.class))).thenReturn(organizationResponse);

        mockMvc.perform(put("/organizations/{id}", organizationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrganizationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(organizationId))
                .andExpect(jsonPath("$.name").value("New Name"));
    }
}
