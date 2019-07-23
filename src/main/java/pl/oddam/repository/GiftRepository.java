package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.Gift;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {

}
