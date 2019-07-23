package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.OrganizationNeed;

@Repository
public interface OrganizationNeedRepository extends JpaRepository<OrganizationNeed, Long> {

}
