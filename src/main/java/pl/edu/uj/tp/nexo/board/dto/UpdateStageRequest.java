package pl.edu.uj.tp.nexo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageRequest {
    private String name;
    private Boolean isActive;
}
