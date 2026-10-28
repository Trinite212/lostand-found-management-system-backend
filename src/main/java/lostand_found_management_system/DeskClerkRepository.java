package lostand_found_management_system;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeskClerkRepository extends JpaRepository<DeskClerk, UUID> {
    @Query("SELECT DISTINCT dc FROM DeskClerk dc JOIN dc.itemCategories ic WHERE LOWER(ic.name) = LOWER(:name)")
    List<DeskClerk> findByItemCategoryNameIgnoreCase(@Param("name") String name);

    boolean existsByCollectionDesk_IdAndIdNot(UUID collectionDeskId, UUID deskClerkId);
}
