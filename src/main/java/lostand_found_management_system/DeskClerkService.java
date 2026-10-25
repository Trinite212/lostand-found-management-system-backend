package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DeskClerkService {
    private final DeskClerkRepository deskClerkRepository;

    public DeskClerkService(DeskClerkRepository deskClerkRepository) {
        this.deskClerkRepository = deskClerkRepository;
    }

    public String save(DeskClerk deskClerk) {
        deskClerkRepository.save(deskClerk);
        return "Desk clerk saved successfully";
    }

    public List<DeskClerk> getAll() {
        return deskClerkRepository.findAll();
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
