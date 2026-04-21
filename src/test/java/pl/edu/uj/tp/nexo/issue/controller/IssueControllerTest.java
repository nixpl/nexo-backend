package pl.edu.uj.tp.nexo.issue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.uj.tp.nexo.issue.dto.IssueResponse;
import pl.edu.uj.tp.nexo.issue.dto.UpdateIssueRequest;
import pl.edu.uj.tp.nexo.issue.entity.IssueType;
import pl.edu.uj.tp.nexo.issue.entity.Priority;
import pl.edu.uj.tp.nexo.issue.service.IssueService;
import pl.edu.uj.tp.nexo.security.SecurityConfig;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IssueController.class)
@Import(SecurityConfig.class)
public class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void updateIssue_whenIssueExists_shouldReturnUpdatedIssue() throws Exception {
        long issueId = 1L;
        UpdateIssueRequest updateIssueRequest = UpdateIssueRequest.builder()
                .title("Updated Title")
                .priority(Priority.HIGH)
                .build();

        IssueResponse issueResponse = IssueResponse.builder()
                .id(issueId)
                .title("Updated Title")
                .priority(Priority.HIGH)
                .type(IssueType.TASK)
                .build();

        when(issueService.updateIssue(eq(issueId), any(UpdateIssueRequest.class))).thenReturn(issueResponse);

        mockMvc.perform(put("/issues/{id}", issueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateIssueRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(issueId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.priority").value(Priority.HIGH.toString()));
    }

    @Test
    public void getIssues_whenNoOrganizationId_shouldReturnAllIssues() throws Exception {
        IssueResponse issueResponse = IssueResponse.builder().id(1L).title("Test Issue").build();
        List<IssueResponse> issueResponses = Collections.singletonList(issueResponse);

        when(issueService.getIssues()).thenReturn(issueResponses);

        mockMvc.perform(get("/issues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Issue"));
    }

    @Test
    public void getIssues_whenOrganizationIdProvided_shouldReturnIssuesForOrganization() throws Exception {
        long organizationId = 1L;
        IssueResponse issueResponse = IssueResponse.builder().id(1L).title("Test Issue").organizationId(organizationId).build();
        List<IssueResponse> issueResponses = Collections.singletonList(issueResponse);

        when(issueService.getIssuesByOrganization(organizationId)).thenReturn(issueResponses);

        mockMvc.perform(get("/issues").param("organizationId", String.valueOf(organizationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Issue"))
                .andExpect(jsonPath("$[0].organizationId").value(organizationId));
    }
}
