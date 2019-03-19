package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationTarget;

@Repository
public interface OrganizationTargetRepository extends JpaRepository<OrganizationTarget, Long> {

}
