package pl.edu.uj.tp.nexo.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.uj.tp.nexo.exception.annotation.ApiErrors;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;
import pl.edu.uj.tp.nexo.auth.dto.AuthenticationRequest;
import pl.edu.uj.tp.nexo.auth.dto.AuthenticationResponse;
import pl.edu.uj.tp.nexo.auth.dto.AdminRegisterRequest;
import pl.edu.uj.tp.nexo.auth.dto.InvitedRegisterRequest;
import pl.edu.uj.tp.nexo.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Authentication and registration API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-admin")
    @Operation(summary = "Register entirely new organization by an Admin", description = "Registers a new user as an admin and creates a new corresponding organization.")
    @ApiErrors({
            ErrorInfo.EMAIL_ALREADY_EXISTS,
            ErrorInfo.INVALID_EMAIL,
            ErrorInfo.INVALID_FIRST_NAME,
            ErrorInfo.INVALID_LAST_NAME,
            ErrorInfo.INVALID_PASSWORD,
            ErrorInfo.INVALID_ORGANIZATION_NAME
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)))
    })
    public ResponseEntity<AuthenticationResponse> registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/register-invited")
    @Operation(summary = "Register by an invited User", description = "Registers a new user if valid invitation invitationToken is provided.")
    @ApiErrors({
            ErrorInfo.INVALID_INVITATION_TOKEN,
            ErrorInfo.EXPIRED_INVITATION,
            ErrorInfo.ORGANIZATION_NOT_FOUND,
            ErrorInfo.EMAIL_ALREADY_EXISTS,
            ErrorInfo.INVALID_FIRST_NAME,
            ErrorInfo.INVALID_LAST_NAME,
            ErrorInfo.INVALID_PASSWORD
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)))
    })
    public ResponseEntity<AuthenticationResponse> registerInvited(@Valid @RequestBody InvitedRegisterRequest request) {
        return ResponseEntity.ok(authService.registerInvited(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and authenticate", description = "Authenticates a user and returns a valid JWT invitationToken.")
    @ApiErrors({
            ErrorInfo.INVALID_CREDENTIALS,
            ErrorInfo.USER_NOT_FOUND
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)))
    })
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}