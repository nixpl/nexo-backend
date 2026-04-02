package pl.edu.uj.tp.nexo.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.tp.nexo.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
