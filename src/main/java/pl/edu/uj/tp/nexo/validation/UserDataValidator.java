package pl.edu.uj.tp.nexo.validation;

import org.springframework.stereotype.Service;
import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

@Service
public class UserDataValidator {

    public void validateEmail(String email) {
        if (email == null || email.isBlank() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new AppException(ErrorInfo.INVALID_EMAIL);
        }
    }

    public void validateName(String name, ErrorInfo errorInfo) {
        if (name == null || name.isBlank() || name.length() < 2 || name.length() > 50 || !name.matches("^[\\p{L}\\s-]+$")) {
            throw new AppException(errorInfo);
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isBlank() || password.length() < 6) {
            throw new AppException(ErrorInfo.INVALID_PASSWORD);
        }
    }

    public void validateOrganizationName(String orgName) {
        if (orgName == null || orgName.isBlank()) {
            throw new AppException(ErrorInfo.INVALID_ORGANIZATION_NAME);
        }
    }
}
