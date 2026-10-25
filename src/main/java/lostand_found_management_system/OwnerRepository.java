package lostand_found_management_system;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    List<Owner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
