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
@RequestMapping("/api/collection-desks")
public class CollectionDeskController {
    private final CollectionDeskService collectionDeskService;

    public CollectionDeskController(CollectionDeskService collectionDeskService) {
        this.collectionDeskService = collectionDeskService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CollectionDesk collectionDesk) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionDeskService.save(collectionDesk));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CollectionDesk>> getAll() {
        return ResponseEntity.ok(collectionDeskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<CollectionDesk> collectionDesk = collectionDeskService.getById(id);
        if (collectionDesk.isPresent()) {
            return ResponseEntity.ok(collectionDesk.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The collection desk with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody CollectionDesk collectionDesk) {
        String message = collectionDeskService.update(id, collectionDesk);
        if (message.equals("The collection desk with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = collectionDeskService.delete(id);
        if (message.equals("The collection desk with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
