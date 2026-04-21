package pl.edu.uj.tp.nexo.organization.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateOrganizationRequest {
    private String name;
}
