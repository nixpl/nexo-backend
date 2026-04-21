package pl.edu.uj.tp.nexo.issue.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.uj.tp.nexo.issue.entity.IssueType;
import pl.edu.uj.tp.nexo.issue.entity.Priority;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueResponse {
    private Long id;
    private String title;
    private String description;
    private String acceptanceCriteria;
    private Long reporterId;
    private Long assigneeId;
    private Integer storyPoints;
    private Priority priority;
    private boolean flag;
    private IssueType type;
    private Long epicId;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private Long boardId;
    private Long stageId;
    private Long organizationId;
}
