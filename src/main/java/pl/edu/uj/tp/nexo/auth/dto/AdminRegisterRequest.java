package pl.edu.uj.tp.nexo.auth.dto;

public record AdminRegisterRequest(
        String organizationName,
        String firstName,
        String lastName,
        String email,
        String password
) {}
