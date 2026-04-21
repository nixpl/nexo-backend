package pl.edu.uj.tp.nexo.board.service;

import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;

public class BoardNotFoundException extends AppException {
    public BoardNotFoundException(Long id) {
        super(ErrorInfo.BOARD_NOT_FOUND);
    }
}
