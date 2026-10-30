package lostand_found_management_system;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LostReportService {
    private final LostReportRepository lostReportRepository;
    private final HandoverNoteRepository handoverNoteRepository;

    public LostReportService(
            LostReportRepository lostReportRepository,
            HandoverNoteRepository handoverNoteRepository) {
        this.lostReportRepository = lostReportRepository;
        this.handoverNoteRepository = handoverNoteRepository;
    }

    public String save(LostReport lostReport) {
        if (lostReport.getReportDate() != null && lostReport.getReportDate().isBefore(LocalDate.now())) {
            return "Lost report date cannot be in the past";
        }
        if (lostReport.getDeskClerk() != null
                && lostReport.getDeskClerk().getId() != null
                && lostReport.getReportDate() != null
                && lostReportRepository.existsByDeskClerkAndReportDateAndStatusNot(
                        lostReport.getDeskClerk(),
                        lostReport.getReportDate(),
                        ReportStatus.WITHDRAWN)) {
            return "Desk clerk already has a report assigned on that date";
        }
        lostReport.setStatus(ReportStatus.REPORTED);
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

    @Transactional
    public Optional<String> updateStatus(UUID id, ReportStatus newStatus) {
        Optional<LostReport> existingLostReport = lostReportRepository.findById(id);
        if (existingLostReport.isEmpty()) {
            return Optional.empty();
        }
        LostReport lostReport = existingLostReport.get();
        ReportStatus currentStatus = lostReport.getStatus();
        boolean allowed = (currentStatus == ReportStatus.REPORTED
                && (newStatus == ReportStatus.MATCHED || newStatus == ReportStatus.WITHDRAWN))
                || (currentStatus == ReportStatus.MATCHED
                && (newStatus == ReportStatus.RETURNED || newStatus == ReportStatus.WITHDRAWN));
        if (!allowed) {
            return Optional.of("Cannot change status from " + currentStatus + " to " + newStatus);
        }
        lostReport.setStatus(newStatus);
        lostReportRepository.save(lostReport);
        return Optional.of("Lost report status updated successfully");
    }

    public String update(UUID id, LostReport updatedLostReport) {
        Optional<LostReport> existingLostReport = lostReportRepository.findById(id);
        if (existingLostReport.isPresent()) {
            LostReport lostReport = existingLostReport.get();
            lostReport.setReportDate(updatedLostReport.getReportDate());
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
        Optional<LostReport> lostReport = lostReportRepository.findById(id);
        if (lostReport.isPresent()) {
            if (handoverNoteRepository.existsByLostReport(lostReport.get())) {
                return "That lost report has handover notes, delete them first";
            }
            lostReportRepository.deleteById(id);
            return "Lost report deleted successfully";
        }
        return "The lost report with that id does not exist";
    }
}
