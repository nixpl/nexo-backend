package pl.edu.uj.tp.nexo.invitation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationRequest;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationResponse;
import pl.edu.uj.tp.nexo.invitation.entity.Invitation;
import pl.edu.uj.tp.nexo.invitation.repository.InvitationRepository;
import pl.edu.uj.tp.nexo.validation.UserDataValidator;
import pl.edu.uj.tp.nexo.organization.repository.OrganizationRepository;
import pl.edu.uj.tp.nexo.organization.entity.Organization;
import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserDataValidator userDataValidator;
    private final EmailService emailService;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public InvitationResponse createInvitation(InvitationRequest request, Long organizationId) {
        userDataValidator.validateEmail(request.email());

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new AppException(ErrorInfo.ORGANIZATION_NOT_FOUND));

        Invitation invitation = new Invitation();
        invitation.setEmail(request.email());
        invitation.setOrganizationId(organizationId);
        invitation.setToken(UUID.randomUUID().toString());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7));
        invitation.setUsed(false);

        invitationRepository.save(invitation);

        emailService.sendInvitationEmail(invitation.getEmail(), invitation.getToken(), organization.getName());

        return new InvitationResponse("Invitation sent successfully to " + request.email());
    }
}
