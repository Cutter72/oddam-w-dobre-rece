package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationNeed;

import java.util.Set;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByName(String name);
    Set<Organization> findAllByNeed(Set<OrganizationNeed> need);
}
