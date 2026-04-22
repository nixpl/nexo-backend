package pl.edu.uj.tp.nexo.board.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.uj.tp.nexo.board.entity.StageType;

@Data
@Builder
public class StageResponse {
    private Long id;
    private String name;
    private StageType type;
    private boolean isActive;
}
