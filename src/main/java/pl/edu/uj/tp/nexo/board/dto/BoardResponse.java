package pl.edu.uj.tp.nexo.board.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BoardResponse {
    private Long id;
    private String name;
    private Long organizationId;
    private List<Long> userIds;
    private List<StageResponse> stages;
    private LocalDateTime createdAt;
}
