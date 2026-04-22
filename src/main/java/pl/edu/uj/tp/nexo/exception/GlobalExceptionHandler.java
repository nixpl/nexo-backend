package pl.edu.uj.tp.nexo.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.uj.tp.nexo.exception.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        ErrorInfo errorInfo = ex.getErrorInfo();
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                errorInfo.getStatus().value(),
                errorInfo.getStatus().getReasonPhrase(),
                errorInfo.getCode(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, errorInfo.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorInfo errorInfo = ErrorInfo.USER_HAS_UNAUTHORIZED_ROLE;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                errorInfo.getStatus().value(),
                errorInfo.getStatus().getReasonPhrase(),
                errorInfo.getCode(),
                errorInfo.getDefaultMessage()
        );
        return new ResponseEntity<>(error, errorInfo.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorInfo errorInfo = ErrorInfo.INVALID_CREDENTIALS;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                errorInfo.getStatus().value(),
                errorInfo.getStatus().getReasonPhrase(),
                errorInfo.getCode(),
                errorInfo.getDefaultMessage()
        );
        return new ResponseEntity<>(error, errorInfo.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorInfo errorInfo = ErrorInfo.RESOURCE_IN_USE;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                errorInfo.getStatus().value(),
                errorInfo.getStatus().getReasonPhrase(),
                errorInfo.getCode(),
                errorInfo.getDefaultMessage()
        );
        return new ResponseEntity<>(error, errorInfo.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace(); // Log the actual error
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ErrorInfo.INTERNAL_ERROR.getCode(),
                ErrorInfo.INTERNAL_ERROR.getDefaultMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}