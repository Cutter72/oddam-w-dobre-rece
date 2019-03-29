package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.City;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationNeed;
import pl.oddam.model.OrganizationTarget;

import java.util.List;
import java.util.Set;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByName(String name);
    Set<Organization> findAllByNameIgnoreCaseContaining(String name);
    Set<Organization> findAllByCity(City city);
    Set<Organization> findAllByNeedIsIn(List<OrganizationNeed> need);
    Set<Organization> findAllByTargetIsIn(List<OrganizationTarget> target);
    Set<Organization> findAllByNeedIsInAndTargetIsIn(List<OrganizationNeed> need, List<OrganizationTarget> target);
    Set<Organization> findAllByCityAndTargetIsIn(City city, List<OrganizationTarget> target);
    Set<Organization> findAllByCityAndNeedIsIn(City city, List<OrganizationNeed> need);
    Set<Organization> findAllByCityAndNeedIsInAndTargetIsIn(City city, List<OrganizationNeed> need, List<OrganizationTarget> target);
}
