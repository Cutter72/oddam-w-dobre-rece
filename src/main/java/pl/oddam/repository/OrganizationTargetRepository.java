package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.OrganizationTarget;

import java.util.List;

@Repository
public interface OrganizationTargetRepository extends JpaRepository<OrganizationTarget, Long> {

}
