package pl.edu.uj.tp.nexo.user.dto;

import lombok.*;
import pl.edu.uj.tp.nexo.user.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
}
