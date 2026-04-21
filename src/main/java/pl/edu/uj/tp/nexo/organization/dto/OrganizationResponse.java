package pl.edu.uj.tp.nexo.organization.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrganizationResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
