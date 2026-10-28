package lostand_found_management_system;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    List<Owner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT o FROM LostReport lr JOIN lr.owner o WHERE lr.deskClerk.id = :deskClerkId")
    List<Owner> findByDeskClerkId(@Param("deskClerkId") UUID deskClerkId);
}
