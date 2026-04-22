package pl.edu.uj.tp.nexo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.uj.tp.nexo.security.config.SecurityConfig;
import pl.edu.uj.tp.nexo.user.dto.UpdateUserRequest;
import pl.edu.uj.tp.nexo.user.dto.UserResponse;
import pl.edu.uj.tp.nexo.user.entity.Role;
import pl.edu.uj.tp.nexo.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUser_whenUserExists_shouldReturnUpdatedUser() throws Exception {
        long userId = 1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .role(Role.ADMIN)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .role(Role.ADMIN)
                .build();

        when(userService.updateUser(eq(userId), any(UpdateUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.role").value(Role.ADMIN.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUsers_whenNoOrganizationId_shouldReturnAllUsers() throws Exception {
        UserResponse userResponse = UserResponse.builder().id(1L).firstName("Jane").build();
        List<UserResponse> userResponses = Collections.singletonList(userResponse);

        when(userService.getUsers()).thenReturn(userResponses);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Jane"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUsers_whenOrganizationIdProvided_shouldReturnUsersForOrganization() throws Exception {
        long organizationId = 1L;
        UserResponse userResponse = UserResponse.builder().id(1L).firstName("Jane").organizationId(organizationId).build();
        List<UserResponse> userResponses = Collections.singletonList(userResponse);

        when(userService.getUsersByOrganization(organizationId)).thenReturn(userResponses);

        mockMvc.perform(get("/users").param("organizationId", String.valueOf(organizationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("$[0].organizationId").value(organizationId));
    }
}
