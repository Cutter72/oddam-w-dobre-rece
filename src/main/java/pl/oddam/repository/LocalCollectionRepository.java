package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.LocalCollection;
import pl.oddam.model.Organization;

@Repository
public interface LocalCollectionRepository extends JpaRepository<LocalCollection, Long> {

}
