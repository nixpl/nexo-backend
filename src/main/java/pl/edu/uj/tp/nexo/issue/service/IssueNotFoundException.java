package pl.edu.uj.tp.nexo.issue.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class IssueNotFoundException extends AppException {
    public IssueNotFoundException(Long id) {
        super(ErrorInfo.ISSUE_NOT_FOUND);
    }
}
