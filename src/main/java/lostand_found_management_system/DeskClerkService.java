package lostand_found_management_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeskClerkService {
    private final DeskClerkRepository deskClerkRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final CollectionDeskRepository collectionDeskRepository;

    public DeskClerkService(
            DeskClerkRepository deskClerkRepository,
            ItemCategoryRepository itemCategoryRepository,
            CollectionDeskRepository collectionDeskRepository) {
        this.deskClerkRepository = deskClerkRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.collectionDeskRepository = collectionDeskRepository;
    }

    public String save(DeskClerk deskClerk) {
        deskClerkRepository.save(deskClerk);
        return "Desk clerk saved successfully";
    }

    public List<DeskClerk> getAll() {
        return deskClerkRepository.findAll();
    }

    public List<DeskClerk> getByItemCategoryName(String name) {
        return deskClerkRepository.findByItemCategoryNameIgnoreCase(name);
    }

    @Transactional
    public String addItemCategory(UUID deskClerkId, UUID itemCategoryId) {
        Optional<DeskClerk> existingDeskClerk = deskClerkRepository.findById(deskClerkId);
        if (existingDeskClerk.isEmpty()) {
            return "The desk clerk with that id does not exist";
        }
        Optional<ItemCategory> existingItemCategory = itemCategoryRepository.findById(itemCategoryId);
        if (existingItemCategory.isEmpty()) {
            return "The item category with that id does not exist";
        }
        DeskClerk deskClerk = existingDeskClerk.get();
        ItemCategory itemCategory = existingItemCategory.get();
        List<ItemCategory> itemCategories = deskClerk.getItemCategories();
        if (itemCategories == null) {
            itemCategories = new ArrayList<>();
            deskClerk.setItemCategories(itemCategories);
        } else if (itemCategories.stream()
                .anyMatch(existingCategory -> existingCategory.getId().equals(itemCategoryId))) {
            return "Desk clerk already has that item category";
        }
        itemCategories.add(itemCategory);
        deskClerkRepository.save(deskClerk);
        return "Item category added to the desk clerk";
    }

    @Transactional
    public String changeCollectionDesk(UUID deskClerkId, int deskNumber) {
        Optional<DeskClerk> existingDeskClerk = deskClerkRepository.findById(deskClerkId);
        if (existingDeskClerk.isEmpty()) {
            return "The desk clerk with that id does not exist";
        }
        Optional<CollectionDesk> collectionDesk = collectionDeskRepository.findByDeskNumber(deskNumber);
        if (collectionDesk.isEmpty()) {
            return "The collection desk with that desk number does not exist";
        }
        DeskClerk deskClerk = existingDeskClerk.get();
        CollectionDesk newCollectionDesk = collectionDesk.get();
        if (deskClerkRepository.existsByCollectionDesk_IdAndIdNot(
                newCollectionDesk.getId(),
                deskClerkId)) {
            return "That collection desk is taken by another desk clerk";
        }
        deskClerk.setCollectionDesk(newCollectionDesk);
        deskClerkRepository.save(deskClerk);
        return "Collection desk changed successfully";
    }

    public Optional<DeskClerk> getById(UUID id) {
        return deskClerkRepository.findById(id);
    }

    public String update(UUID id, DeskClerk updatedDeskClerk) {
        Optional<DeskClerk> existingDeskClerk = deskClerkRepository.findById(id);
        if (existingDeskClerk.isPresent()) {
            DeskClerk deskClerk = existingDeskClerk.get();
            deskClerk.setFirstName(updatedDeskClerk.getFirstName());
            deskClerk.setLastName(updatedDeskClerk.getLastName());
            deskClerk.setEmail(updatedDeskClerk.getEmail());
            deskClerk.setCollectionDesk(updatedDeskClerk.getCollectionDesk());
            deskClerk.setItemCategories(updatedDeskClerk.getItemCategories());
            deskClerkRepository.save(deskClerk);
            return "Desk clerk updated successfully";
        }
        return "The desk clerk with that id does not exist";
    }

    public String delete(UUID id) {
        if (deskClerkRepository.existsById(id)) {
            deskClerkRepository.deleteById(id);
            return "Desk clerk deleted successfully";
        }
        return "The desk clerk with that id does not exist";
    }
}
