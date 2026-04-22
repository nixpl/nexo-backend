package pl.edu.uj.tp.nexo.board.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateBoardRequest {
    private String name;
    private Long organizationId;
    private List<Long> userIds;
}
