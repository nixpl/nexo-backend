package pl.edu.uj.tp.nexo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponse {
    private Long id;
    private String name;
    private Long organizationId;
    private List<Long> userIds;
    private List<StageResponse> stages;
    private LocalDateTime createdAt;
}
