package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.oddam.model.Gift;
import pl.oddam.model.User;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findAllByUser(User user);
    @Query(nativeQuery = true, value = "SELECT SUM(bags) FROM gift;")
    Integer countBags();
    @Query(nativeQuery = true, value = "SELECT COUNT(*) from (select gift.organization_id from gift group by organization_id) as supportedOrganizations;")
    Integer countSupportedOrganizations();
    @Query(nativeQuery = true, value = "SELECT COUNT(*) from gift;")
    Integer countAll();
}
