package pl.edu.uj.tp.nexo.board.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class StageNotFoundException extends AppException {
    public StageNotFoundException(Long id) {
        super(ErrorInfo.STAGE_NOT_FOUND);
    }
}
