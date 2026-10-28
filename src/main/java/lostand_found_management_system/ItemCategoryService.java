package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public String save(ItemCategory itemCategory) {
        itemCategoryRepository.save(itemCategory);
        return "Item category saved successfully";
    }

    public List<ItemCategory> getAll() {
        return itemCategoryRepository.findAll();
    }

    public List<ItemCategory> getUnusedCategories() {
        return itemCategoryRepository.findUnusedCategories();
    }

    public Optional<ItemCategory> getById(UUID id) {
        return itemCategoryRepository.findById(id);
    }

    public String update(UUID id, ItemCategory updatedItemCategory) {
        Optional<ItemCategory> existingItemCategory = itemCategoryRepository.findById(id);
        if (existingItemCategory.isPresent()) {
            ItemCategory itemCategory = existingItemCategory.get();
            itemCategory.setName(updatedItemCategory.getName());
            itemCategory.setDeskClerks(updatedItemCategory.getDeskClerks());
            itemCategoryRepository.save(itemCategory);
            return "Item category updated successfully";
        }
        return "The item category with that id does not exist";
    }

    public String delete(UUID id) {
        if (itemCategoryRepository.existsById(id)) {
            itemCategoryRepository.deleteById(id);
            return "Item category deleted successfully";
        }
        return "The item category with that id does not exist";
    }
}
