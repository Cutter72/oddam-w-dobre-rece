package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.City;
import pl.oddam.model.Organization;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
