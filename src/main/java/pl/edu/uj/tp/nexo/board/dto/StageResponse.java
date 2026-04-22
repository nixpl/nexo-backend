package pl.edu.uj.tp.nexo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.uj.tp.nexo.board.entity.StageType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageResponse {
    private Long id;
    private String name;
    private StageType type;
    private boolean isActive;
}
