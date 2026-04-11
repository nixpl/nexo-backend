package pl.edu.uj.tp.nexo.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorInfo errorInfo;

    public AppException(ErrorInfo errorInfo) {
        super(errorInfo.getDefaultMessage());
        this.errorInfo = errorInfo;
    }
}
