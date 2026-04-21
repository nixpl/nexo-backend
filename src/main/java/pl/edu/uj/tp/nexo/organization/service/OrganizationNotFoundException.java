package pl.edu.uj.tp.nexo.organization.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class OrganizationNotFoundException extends AppException {
    public OrganizationNotFoundException(Long id) {
        super(ErrorInfo.ORGANIZATION_NOT_FOUND);
    }
}
