package pl.edu.uj.tp.nexo.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorInfo {

    // ------------------------------------------
    // 1000 - 1999: Users and Authentication (Auth/User)
    // ------------------------------------------
    USER_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "User not found"),
    INVALID_CREDENTIALS(1002, HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    USER_HAS_UNAUTHORIZED_ROLE(1003, HttpStatus.FORBIDDEN, "User does not have the required role"),
    EMAIL_ALREADY_EXISTS(1004, HttpStatus.CONFLICT, "User with this email already exists"),
    INVALID_TOKEN(1005, HttpStatus.UNAUTHORIZED, "Invalid or expired invitationToken"),

    // ------------------------------------------
    // 2000 - 2999: Tasks (Task)
    // ------------------------------------------

    // ------------------------------------------
    // 3000 - 3999: General errors, Validation, Others
    // ------------------------------------------
    INVALID_INVITATION_TOKEN(3001, HttpStatus.BAD_REQUEST, "Invalid invitation invitationToken"),
    EXPIRED_INVITATION(3002, HttpStatus.BAD_REQUEST, "Invitation is expired or already used"),
    ORGANIZATION_NOT_FOUND(3003, HttpStatus.NOT_FOUND, "Organization not found"),

    INVALID_FIRST_NAME(3004, HttpStatus.BAD_REQUEST, "First name must be between 2 and 50 characters, and contain only letters, spaces, or hyphens"),
    INVALID_LAST_NAME(3005, HttpStatus.BAD_REQUEST, "Last name must be between 2 and 50 characters, and contain only letters, spaces, or hyphens"),
    INVALID_EMAIL(3006, HttpStatus.BAD_REQUEST, "Invalid email format. Email cannot be blank and must contain @ symbol"),
    INVALID_PASSWORD(3007, HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long and cannot be blank"),
    INVALID_ORGANIZATION_NAME(3008, HttpStatus.BAD_REQUEST, "Organization name cannot be blank"),

    // *** Add more error codes as needed, following the same pattern:

    // ------------------------------------------
    // 9000 - 9999: System and Critical errors
    // ------------------------------------------
    INTERNAL_ERROR(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final int code;
    private final HttpStatus status;
    private final String defaultMessage;
}
