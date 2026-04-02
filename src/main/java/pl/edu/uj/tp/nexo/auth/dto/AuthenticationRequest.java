package pl.edu.uj.tp.nexo.auth.dto;

public record AuthenticationRequest(
        String email,
        String password
) {}