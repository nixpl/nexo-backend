package pl.edu.uj.tp.nexo.board.dto;

import lombok.Data;

@Data
public class UpdateStageRequest {
    private String name;
    private Boolean isActive;
}
