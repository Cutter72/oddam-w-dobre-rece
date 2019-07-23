package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.LocalCollection;

@Repository
public interface LocalCollectionRepository extends JpaRepository<LocalCollection, Long> {

}
