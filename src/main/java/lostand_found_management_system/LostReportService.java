package lostand_found_management_system;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LostReportService {
    private final LostReportRepository lostReportRepository;

    public LostReportService(LostReportRepository lostReportRepository) {
        this.lostReportRepository = lostReportRepository;
    }

    public String save(LostReport lostReport) {
        lostReportRepository.save(lostReport);
        return "Lost report saved successfully";
    }

    public List<LostReport> getAll() {
        return lostReportRepository.findAll();
    }

    public List<LostReport> getByStatus(ReportStatus status) {
        return lostReportRepository.findByStatusOrderByReportDateAsc(status);
    }

    public List<LostReport> getBetweenDates(LocalDate start, LocalDate end) {
        return lostReportRepository.findByReportDateBetweenOrderByReportDateAsc(start, end);
    }

    public Optional<LostReport> getById(UUID id) {
        return lostReportRepository.findById(id);
    }

    public String update(UUID id, LostReport updatedLostReport) {
        Optional<LostReport> existingLostReport = lostReportRepository.findById(id);
        if (existingLostReport.isPresent()) {
            LostReport lostReport = existingLostReport.get();
            lostReport.setReportDate(updatedLostReport.getReportDate());
            lostReport.setStatus(updatedLostReport.getStatus());
            lostReport.setItemName(updatedLostReport.getItemName());
            lostReport.setLostLocation(updatedLostReport.getLostLocation());
            lostReport.setOwner(updatedLostReport.getOwner());
            lostReport.setDeskClerk(updatedLostReport.getDeskClerk());
            lostReportRepository.save(lostReport);
            return "Lost report updated successfully";
        }
        return "The lost report with that id does not exist";
    }

    public String delete(UUID id) {
        if (lostReportRepository.existsById(id)) {
            lostReportRepository.deleteById(id);
            return "Lost report deleted successfully";
        }
        return "The lost report with that id does not exist";
    }
}
