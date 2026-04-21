package pl.edu.uj.tp.nexo.user.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(Long id) {
        super(ErrorInfo.USER_NOT_FOUND);
    }
}
