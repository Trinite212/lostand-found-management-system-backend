package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(lostReportService.save(lostReport));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LostReport>> getAll() {
        return ResponseEntity.ok(lostReportService.getAll());
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
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
