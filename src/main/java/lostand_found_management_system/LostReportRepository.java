package lostand_found_management_system;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostReportRepository extends JpaRepository<LostReport, UUID> {
    List<LostReport> findByStatusOrderByReportDateAsc(ReportStatus status);

    List<LostReport> findByReportDateBetweenOrderByReportDateAsc(LocalDate start, LocalDate end);
}
