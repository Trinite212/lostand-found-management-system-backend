package lostand_found_management_system;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HandoverNoteRepository extends JpaRepository<HandoverNote, UUID> {
    @Query("SELECT d FROM HandoverNote d WHERE d.lostReport.owner.nationalId = :nationalId "
            + "ORDER BY d.handoverDate DESC")
    List<HandoverNote> findByOwnerNationalId(@Param("nationalId") String nationalId);

    boolean existsByLostReport(LostReport lostReport);
}
