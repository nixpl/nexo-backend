package pl.edu.uj.tp.nexo.issue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.edu.uj.tp.nexo.issue.dto.CreateIssueRequest;
import pl.edu.uj.tp.nexo.issue.dto.IssueResponse;
import pl.edu.uj.tp.nexo.issue.dto.UpdateIssueRequest;
import pl.edu.uj.tp.nexo.issue.service.IssueService;

import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<IssueResponse> getIssues(@RequestParam(required = false) Long organizationId) {
        if (organizationId != null) {
            return issueService.getIssuesByOrganization(organizationId);
        }
        return issueService.getIssues();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public IssueResponse getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public IssueResponse createIssue(@RequestBody CreateIssueRequest request) {
        return issueService.createIssue(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public IssueResponse updateIssue(@PathVariable Long id, @RequestBody UpdateIssueRequest request) {
        return issueService.updateIssue(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}
