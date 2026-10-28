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
@RequestMapping("/api/item-categories")
public class ItemCategoryController {
    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ItemCategory itemCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCategoryService.save(itemCategory));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemCategory>> getAll() {
        return ResponseEntity.ok(itemCategoryService.getAll());
    }

    @GetMapping("/unused")
    public ResponseEntity<List<ItemCategory>> getUnusedCategories() {
        return ResponseEntity.ok(itemCategoryService.getUnusedCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ItemCategory> itemCategory = itemCategoryService.getById(id);
        if (itemCategory.isPresent()) {
            return ResponseEntity.ok(itemCategory.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody ItemCategory itemCategory) {
        String message = itemCategoryService.update(id, itemCategory);
        if (message.equals("The item category with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = itemCategoryService.delete(id);
        if (message.equals("The item category with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
