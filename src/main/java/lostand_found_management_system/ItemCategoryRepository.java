package lostand_found_management_system;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {
}
