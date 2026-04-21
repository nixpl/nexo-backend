package pl.edu.uj.tp.nexo.user.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class UserAlreadyExistsException extends AppException {
    public UserAlreadyExistsException(String email) {
        super(ErrorInfo.EMAIL_ALREADY_EXISTS);
    }
}
