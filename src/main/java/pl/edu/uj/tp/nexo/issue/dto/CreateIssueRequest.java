package pl.edu.uj.tp.nexo.issue.dto;

import lombok.Data;
import pl.edu.uj.tp.nexo.issue.entity.IssueType;
import pl.edu.uj.tp.nexo.issue.entity.Priority;

import java.time.LocalDateTime;

@Data
public class CreateIssueRequest {
    private String title;
    private String description;
    private String acceptanceCriteria;
    private Long reporterId;
    private Long assigneeId;
    private Integer storyPoints;
    private Priority priority;
    private IssueType type;
    private Long epicId;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private Long boardId;
    private Long stageId;
    private Long organizationId;
}
