package pl.edu.uj.tp.nexo.user.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edu.uj.tp.nexo.user.entity.Role;

@Getter
@Builder
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
}
