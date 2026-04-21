package pl.edu.uj.tp.nexo.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.tp.nexo.issue.entity.Issue;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByOrganizationId(Long organizationId);
}
