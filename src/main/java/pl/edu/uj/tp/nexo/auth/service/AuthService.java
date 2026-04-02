package pl.edu.uj.tp.nexo.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.uj.tp.nexo.auth.dto.AuthenticationRequest;
import pl.edu.uj.tp.nexo.auth.dto.AuthenticationResponse;
import pl.edu.uj.tp.nexo.security.service.JwtService;
import pl.edu.uj.tp.nexo.user.entity.Role;
import pl.edu.uj.tp.nexo.user.entity.User;
import pl.edu.uj.tp.nexo.organization.entity.Organization;
import pl.edu.uj.tp.nexo.invitation.entity.Invitation;
import pl.edu.uj.tp.nexo.user.repository.UserRepository;
import pl.edu.uj.tp.nexo.organization.repository.OrganizationRepository;
import pl.edu.uj.tp.nexo.invitation.repository.InvitationRepository;
import pl.edu.uj.tp.nexo.exception.AppException;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;
import org.springframework.security.core.AuthenticationException;
import pl.edu.uj.tp.nexo.auth.dto.AdminRegisterRequest;
import pl.edu.uj.tp.nexo.auth.dto.InvitedRegisterRequest;
import pl.edu.uj.tp.nexo.validation.UserDataValidator;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final InvitationRepository invitationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDataValidator userDataValidator;

    public AuthenticationResponse registerAdmin(AdminRegisterRequest request) {
        userDataValidator.validateEmail(request.email());
        userDataValidator.validateName(request.firstName(), ErrorInfo.INVALID_FIRST_NAME);
        userDataValidator.validateName(request.lastName(), ErrorInfo.INVALID_LAST_NAME);
        userDataValidator.validatePassword(request.password());
        userDataValidator.validateOrganizationName(request.organizationName());

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new AppException(ErrorInfo.EMAIL_ALREADY_EXISTS);
        }

        Organization org = new Organization();
        org.setName(request.organizationName());
        org = organizationRepository.save(org);

        var user = User.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .organization(org)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse registerInvited(InvitedRegisterRequest request) {
        userDataValidator.validateName(request.firstName(), ErrorInfo.INVALID_FIRST_NAME);
        userDataValidator.validateName(request.lastName(), ErrorInfo.INVALID_LAST_NAME);
        userDataValidator.validatePassword(request.password());

        Invitation invitation = invitationRepository.findByToken(request.invitationToken())
                .orElseThrow(() -> new AppException(ErrorInfo.INVALID_INVITATION_TOKEN));

        if (invitation.isUsed() || invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorInfo.EXPIRED_INVITATION);
        }

        if (userRepository.findByEmail(invitation.getEmail()).isPresent()) {
            throw new AppException(ErrorInfo.EMAIL_ALREADY_EXISTS);
        }

        Organization org = organizationRepository.findById(invitation.getOrganizationId())
                .orElseThrow(() -> new AppException(ErrorInfo.ORGANIZATION_NOT_FOUND));

        var user = User.builder()
                .email(invitation.getEmail())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .organization(org)
                .build();

        userRepository.save(user);

        invitation.setUsed(true);
        invitationRepository.save(invitation);

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new AppException(ErrorInfo.INVALID_CREDENTIALS);
        }

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(ErrorInfo.USER_NOT_FOUND));
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}