package pl.edu.uj.tp.nexo.issue.dto;

import lombok.*;
import pl.edu.uj.tp.nexo.issue.entity.Priority;
import pl.edu.uj.tp.nexo.issue.entity.IssueType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIssueRequest {
    private String title;
    private String description;
    private String acceptanceCriteria;
    private Long assigneeId;
    private Integer storyPoints;
    private Priority priority;
    private boolean flag;
    private IssueType type;
    private Long epicId;
    private LocalDate startDate;
    private LocalDate deadline;
    private Long stageId;
}
