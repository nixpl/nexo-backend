package pl.edu.uj.tp.nexo.invitation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.tp.nexo.invitation.entity.Invitation;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByToken(String token);
}
