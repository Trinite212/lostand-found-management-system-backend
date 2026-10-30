package lostand_found_management_system;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/handover-notes")
public class HandoverNoteController {
    private final HandoverNoteService handoverNoteService;

    public HandoverNoteController(HandoverNoteService handoverNoteService) {
        this.handoverNoteService = handoverNoteService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam UUID lostReportId,
            @RequestBody HandoverNote handoverNote) {
        String message = handoverNoteService.save(lostReportId, handoverNote);
        if (message.equals("The lost report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Handover notes are only for RETURNED lost reports")) {
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/owner/{nationalId}")
    public ResponseEntity<List<HandoverNote>> getByOwnerNationalId(@PathVariable String nationalId) {
        return ResponseEntity.ok(handoverNoteService.getByOwnerNationalId(nationalId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = handoverNoteService.delete(id);
        if (message.equals("The handover note with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
