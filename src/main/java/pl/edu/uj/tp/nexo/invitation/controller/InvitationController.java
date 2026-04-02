package pl.edu.uj.tp.nexo.invitation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import pl.edu.uj.tp.nexo.exception.annotation.ApiErrors;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;
import pl.edu.uj.tp.nexo.exception.dto.ErrorResponse;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationRequest;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationResponse;
import pl.edu.uj.tp.nexo.invitation.service.InvitationService;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
@Tag(name = "InvitationController", description = "Operations related to user invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create an invitation", description = "Generates a unique invitation invitationToken for a provided email address. (Requires ADMIN role)")
    @ApiErrors({
            ErrorInfo.USER_HAS_UNAUTHORIZED_ROLE,
            ErrorInfo.INVALID_EMAIL
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitation created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvitationResponse.class)))
    })
    public ResponseEntity<InvitationResponse> createInvitation(@RequestBody InvitationRequest request) {
        return ResponseEntity.ok(invitationService.createInvitation(request));
    }
}
