package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectionDeskRepository extends JpaRepository<CollectionDesk, UUID> {
    @Query("SELECT cd FROM CollectionDesk cd WHERE NOT EXISTS (SELECT dc.id FROM DeskClerk dc WHERE dc.collectionDesk = cd) ORDER BY cd.deskNumber ASC")
    List<CollectionDesk> findFreeDesks();

    Optional<CollectionDesk> findByDeskNumber(int deskNumber);
}
