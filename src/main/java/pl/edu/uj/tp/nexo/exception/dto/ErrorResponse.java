package pl.edu.uj.tp.nexo.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String statusName,
        int errorCode,
        String message
) {}