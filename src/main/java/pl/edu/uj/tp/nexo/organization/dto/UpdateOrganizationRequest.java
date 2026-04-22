package pl.edu.uj.tp.nexo.organization.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrganizationRequest {
    private String name;
}
