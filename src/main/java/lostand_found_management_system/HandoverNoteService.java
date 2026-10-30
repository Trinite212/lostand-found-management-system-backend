package lostand_found_management_system;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class HandoverNoteService {
    private final HandoverNoteRepository handoverNoteRepository;
    private final LostReportRepository lostReportRepository;

    public HandoverNoteService(
            HandoverNoteRepository handoverNoteRepository,
            LostReportRepository lostReportRepository) {
        this.handoverNoteRepository = handoverNoteRepository;
        this.lostReportRepository = lostReportRepository;
    }

    public String save(UUID lostReportId, HandoverNote handoverNote) {
        return lostReportRepository.findById(lostReportId)
                .map(lostReport -> {
                    if (lostReport.getStatus() != ReportStatus.RETURNED) {
                        return "Handover notes are only for RETURNED lost reports";
                    }
                    handoverNote.setId(null);
                    handoverNote.setHandoverDate(LocalDate.now());
                    handoverNote.setLostReport(lostReport);
                    handoverNoteRepository.save(handoverNote);
                    return "Handover note saved successfully";
                })
                .orElse("The lost report with that id does not exist");
    }

    public List<HandoverNote> getByOwnerNationalId(String nationalId) {
        return handoverNoteRepository.findByOwnerNationalId(nationalId);
    }

    public String delete(UUID id) {
        if (handoverNoteRepository.existsById(id)) {
            handoverNoteRepository.deleteById(id);
            return "Handover note deleted successfully";
        }
        return "The handover note with that id does not exist";
    }
}
