package pl.edu.uj.tp.nexo.issue.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.uj.tp.nexo.board.entity.Board;
import pl.edu.uj.tp.nexo.board.entity.Stage;
import pl.edu.uj.tp.nexo.user.entity.User;
import pl.edu.uj.tp.nexo.organization.entity.Organization;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String acceptanceCriteria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    private Integer storyPoints;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    private boolean flag = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    private Issue epic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime startDate;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}
