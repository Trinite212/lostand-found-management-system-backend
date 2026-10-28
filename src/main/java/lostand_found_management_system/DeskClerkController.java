package lostand_found_management_system;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/desk-clerks")
public class DeskClerkController {
    private final DeskClerkService deskClerkService;

    public DeskClerkController(DeskClerkService deskClerkService) {
        this.deskClerkService = deskClerkService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DeskClerk deskClerk) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deskClerkService.save(deskClerk));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeskClerk>> getAll() {
        return ResponseEntity.ok(deskClerkService.getAll());
    }

    @GetMapping("/by-item-category")
    public ResponseEntity<List<DeskClerk>> getByItemCategoryName(@RequestParam("name") String name) {
        return ResponseEntity.ok(deskClerkService.getByItemCategoryName(name));
    }

    @PutMapping("/{deskClerkId}/item-categories/{itemCategoryId}")
    public ResponseEntity<String> addItemCategory(
            @PathVariable UUID deskClerkId,
            @PathVariable UUID itemCategoryId) {
        String message = deskClerkService.addItemCategory(deskClerkId, itemCategoryId);
        if (message.equals("The desk clerk with that id does not exist")
                || message.equals("The item category with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Desk clerk already has that item category")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{deskClerkId}/collection-desk")
    public ResponseEntity<String> changeCollectionDesk(
            @PathVariable UUID deskClerkId,
            @RequestParam("deskNumber") int deskNumber) {
        String message = deskClerkService.changeCollectionDesk(deskClerkId, deskNumber);
        if (message.equals("The desk clerk with that id does not exist")
                || message.equals("The collection desk with that desk number does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That collection desk is taken by another desk clerk")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<DeskClerk> deskClerk = deskClerkService.getById(id);
        if (deskClerk.isPresent()) {
            return ResponseEntity.ok(deskClerk.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The desk clerk with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody DeskClerk deskClerk) {
        String message = deskClerkService.update(id, deskClerk);
        if (message.equals("The desk clerk with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = deskClerkService.delete(id);
        if (message.equals("The desk clerk with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
