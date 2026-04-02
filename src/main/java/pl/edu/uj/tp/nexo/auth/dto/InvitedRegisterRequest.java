package pl.edu.uj.tp.nexo.auth.dto;

public record InvitedRegisterRequest(
        String invitationToken,
        String firstName,
        String lastName,
        String password
) {}
