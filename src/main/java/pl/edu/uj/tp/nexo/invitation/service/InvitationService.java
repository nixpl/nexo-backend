package pl.edu.uj.tp.nexo.invitation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationRequest;
import pl.edu.uj.tp.nexo.invitation.dto.InvitationResponse;
import pl.edu.uj.tp.nexo.invitation.entity.Invitation;
import pl.edu.uj.tp.nexo.invitation.repository.InvitationRepository;
import pl.edu.uj.tp.nexo.validation.UserDataValidator;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserDataValidator userDataValidator;

    @Transactional
    public InvitationResponse createInvitation(InvitationRequest request, Long organizationId) {
        userDataValidator.validateEmail(request.email());

        Invitation invitation = new Invitation();
        invitation.setEmail(request.email());
        invitation.setOrganizationId(organizationId);
        invitation.setToken(UUID.randomUUID().toString());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7));
        invitation.setUsed(false);

        invitationRepository.save(invitation);

        return new InvitationResponse(invitation.getToken());
    }
}
