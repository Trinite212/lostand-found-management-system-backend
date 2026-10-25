package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CollectionDeskService {
    private final CollectionDeskRepository collectionDeskRepository;

    public CollectionDeskService(CollectionDeskRepository collectionDeskRepository) {
        this.collectionDeskRepository = collectionDeskRepository;
    }

    public String save(CollectionDesk collectionDesk) {
        collectionDeskRepository.save(collectionDesk);
        return "Collection desk saved successfully";
    }

    public List<CollectionDesk> getAll() {
        return collectionDeskRepository.findAll();
    }

    public Optional<CollectionDesk> getById(UUID id) {
        return collectionDeskRepository.findById(id);
    }

    public String update(UUID id, CollectionDesk updatedCollectionDesk) {
        Optional<CollectionDesk> existingCollectionDesk = collectionDeskRepository.findById(id);
        if (existingCollectionDesk.isPresent()) {
            CollectionDesk collectionDesk = existingCollectionDesk.get();
            collectionDesk.setDeskNumber(updatedCollectionDesk.getDeskNumber());
            collectionDesk.setName(updatedCollectionDesk.getName());
            collectionDeskRepository.save(collectionDesk);
            return "Collection desk updated successfully";
        }
        return "The collection desk with that id does not exist";
    }

    public String delete(UUID id) {
        if (collectionDeskRepository.existsById(id)) {
            collectionDeskRepository.deleteById(id);
            return "Collection desk deleted successfully";
        }
        return "The collection desk with that id does not exist";
    }
}
