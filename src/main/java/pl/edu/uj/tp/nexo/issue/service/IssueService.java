package pl.edu.uj.tp.nexo.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.tp.nexo.board.entity.Stage;
import pl.edu.uj.tp.nexo.board.entity.StageType;
import pl.edu.uj.tp.nexo.board.repository.BoardRepository;
import pl.edu.uj.tp.nexo.board.repository.StageRepository;
import pl.edu.uj.tp.nexo.board.service.BoardNotFoundException;
import pl.edu.uj.tp.nexo.board.service.StageNotFoundException;
import pl.edu.uj.tp.nexo.issue.dto.CreateIssueRequest;
import pl.edu.uj.tp.nexo.issue.dto.IssueResponse;
import pl.edu.uj.tp.nexo.issue.dto.UpdateIssueRequest;
import pl.edu.uj.tp.nexo.issue.entity.Issue;
import pl.edu.uj.tp.nexo.issue.repository.IssueRepository;
import pl.edu.uj.tp.nexo.organization.repository.OrganizationRepository;
import pl.edu.uj.tp.nexo.organization.service.OrganizationNotFoundException;
import pl.edu.uj.tp.nexo.user.repository.UserRepository;
import pl.edu.uj.tp.nexo.user.service.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final StageRepository stageRepository;
    private final OrganizationRepository organizationRepository;

    public List<IssueResponse> searchIssues(Long organizationId, Long boardId, Long stageId, Long assigneeId, String search) {
        Specification<Issue> spec = Specification.where((Specification<Issue>) null);

        if (organizationId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("organization").get("id"), organizationId));
        }
        if (boardId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("board").get("id"), boardId));
        }
        if (stageId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stage").get("id"), stageId));
        }
        if (assigneeId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("assignee").get("id"), assigneeId));
        }
        if (search != null && !search.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%")
            ));
        }

        return issueRepository.findAll(spec).stream()
                .map(this::toIssueResponse)
                .collect(Collectors.toList());
    }

    public List<IssueResponse> getIssues() {
        return issueRepository.findAll().stream()
                .map(this::toIssueResponse)
                .collect(Collectors.toList());
    }

    public List<IssueResponse> getIssuesByOrganization(Long organizationId) {
        if (!organizationRepository.existsById(organizationId)) {
            throw new OrganizationNotFoundException(organizationId);
        }
        return issueRepository.findAllByOrganizationId(organizationId).stream()
                .map(this::toIssueResponse)
                .collect(Collectors.toList());
    }

    public IssueResponse getIssueById(Long id) {
        return issueRepository.findById(id)
                .map(this::toIssueResponse)
                .orElseThrow(() -> new IssueNotFoundException(id));
    }

    public IssueResponse createIssue(CreateIssueRequest request) {
        var reporter = userRepository.findById(request.getReporterId()).orElseThrow(() -> new UserNotFoundException(request.getReporterId()));
        var assignee = request.getAssigneeId() != null ? userRepository.findById(request.getAssigneeId()).orElseThrow(() -> new UserNotFoundException(request.getAssigneeId())) : null;
        var board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new BoardNotFoundException(request.getBoardId()));
        var stage = stageRepository.findById(request.getStageId()).orElseThrow(() -> new StageNotFoundException(request.getStageId()));
        var organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow(() -> new OrganizationNotFoundException(request.getOrganizationId()));
        var epic = request.getEpicId() != null ? issueRepository.findById(request.getEpicId()).orElseThrow(() -> new IssueNotFoundException(request.getEpicId())) : null;

        validateEpicStage(request.getType(), stage);

        Issue issue = Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .acceptanceCriteria(request.getAcceptanceCriteria())
                .reporter(reporter)
                .assignee(assignee)
                .storyPoints(request.getStoryPoints())
                .priority(request.getPriority())
                .type(request.getType())
                .epic(epic)
                .startDate(request.getStartDate())
                .deadline(request.getDeadline())
                .board(board)
                .stage(stage)
                .organization(organization)
                .build();

        issue = issueRepository.save(issue);
        return toIssueResponse(issue);
    }

    public IssueResponse updateIssue(Long id, UpdateIssueRequest request) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new IssueNotFoundException(id));

        if (request.getTitle() != null) {
            issue.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            issue.setDescription(request.getDescription());
        }
        if (request.getAcceptanceCriteria() != null) {
            issue.setAcceptanceCriteria(request.getAcceptanceCriteria());
        }
        if (request.getAssigneeId() != null) {
            var assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new UserNotFoundException(request.getAssigneeId()));
            issue.setAssignee(assignee);
        }
        if (request.getStoryPoints() != null) {
            issue.setStoryPoints(request.getStoryPoints());
        }
        if (request.getPriority() != null) {
            issue.setPriority(request.getPriority());
        }
        
        issue.setFlag(request.isFlag());

        if (request.getType() != null) {
            issue.setType(request.getType());
        }
        if (request.getEpicId() != null) {
            var epic = issueRepository.findById(request.getEpicId())
                    .orElseThrow(() -> new IssueNotFoundException(request.getEpicId()));
            issue.setEpic(epic);
        }
        if (request.getStartDate() != null) {
            issue.setStartDate(request.getStartDate().atStartOfDay());
        }
        if (request.getDeadline() != null) {
            issue.setDeadline(request.getDeadline().atStartOfDay());
        }
        if (request.getStageId() != null) {
            var stage = stageRepository.findById(request.getStageId())
                    .orElseThrow(() -> new StageNotFoundException(request.getStageId()));
            validateEpicStage(issue.getType(), stage);
            issue.setStage(stage);
        }

        issue = issueRepository.save(issue);
        return toIssueResponse(issue);
    }

    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new IssueNotFoundException(id);
        }
        issueRepository.deleteById(id);
    }

    private void validateEpicStage(pl.edu.uj.tp.nexo.issue.entity.IssueType type, Stage stage) {
        if (type == pl.edu.uj.tp.nexo.issue.entity.IssueType.EPIC) {
            List<StageType> allowed = List.of(StageType.PREPARATION, StageType.TO_DO, StageType.IN_PROGRESS, StageType.DONE);
            if (!allowed.contains(stage.getType())) {
                throw new IllegalArgumentException("Epics can only be assigned to: " + allowed);
            }
        }
    }

    private IssueResponse toIssueResponse(Issue issue) {
        return IssueResponse.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .acceptanceCriteria(issue.getAcceptanceCriteria())
                .reporterId(issue.getReporter() != null ? issue.getReporter().getId() : null)
                .assigneeId(issue.getAssignee() != null ? issue.getAssignee().getId() : null)
                .storyPoints(issue.getStoryPoints())
                .priority(issue.getPriority())
                .flag(issue.isFlag())
                .type(issue.getType())
                .epicId(issue.getEpic() != null ? issue.getEpic().getId() : null)
                .createdAt(issue.getCreatedAt())
                .startDate(issue.getStartDate())
                .deadline(issue.getDeadline())
                .boardId(issue.getBoard() != null ? issue.getBoard().getId() : null)
                .stageId(issue.getStage() != null ? issue.getStage().getId() : null)
                .organizationId(issue.getOrganization() != null ? issue.getOrganization().getId() : null)
                .build();
    }
}
