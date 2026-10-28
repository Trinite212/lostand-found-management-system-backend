package lostand_found_management_system;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {
    @Query("SELECT ic FROM ItemCategory ic WHERE ic.deskClerks IS EMPTY")
    List<ItemCategory> findUnusedCategories();
}
