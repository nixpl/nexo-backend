package pl.edu.uj.tp.nexo.user.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.uj.tp.nexo.user.entity.Role;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long organizationId;
    private Role role;
}
