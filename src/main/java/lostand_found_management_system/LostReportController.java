package lostand_found_management_system;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lost-reports")
public class LostReportController {
    private final LostReportService lostReportService;

    public LostReportController(LostReportService lostReportService) {
        this.lostReportService = lostReportService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LostReport lostReport) {
        String message = lostReportService.save(lostReport);
        if (message.equals("Lost report date cannot be in the past")) {
            return ResponseEntity.badRequest().body(message);
        }
        if (message.equals("Desk clerk already has a report assigned on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LostReport>> getAll() {
        return ResponseEntity.ok(lostReportService.getAll());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<LostReport>> getByStatus(@RequestParam ReportStatus status) {
        return ResponseEntity.ok(lostReportService.getByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<LostReport>> getBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok(lostReportService.getBetweenDates(
                LocalDate.parse(start),
                LocalDate.parse(end)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<LostReport> lostReport = lostReportService.getById(id);
        if (lostReport.isPresent()) {
            return ResponseEntity.ok(lostReport.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The lost report with that id does not exist");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable UUID id,
            @RequestParam("status") ReportStatus status) {
        Optional<String> message = lostReportService.updateStatus(id, status);
        if (message.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The lost report with that id does not exist");
        }
        if (message.get().startsWith("Cannot change status from ")) {
            return ResponseEntity.badRequest().body(message.get());
        }
        return ResponseEntity.ok(message.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody LostReport lostReport) {
        String message = lostReportService.update(id, lostReport);
        if (message.equals("The lost report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = lostReportService.delete(id);
        if (message.equals("The lost report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("That lost report has handover notes, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
