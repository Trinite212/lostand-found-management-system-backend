package lostand_found_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public String save(Owner owner) {
        ownerRepository.save(owner);
        return "Owner saved successfully";
    }

    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> getById(UUID id) {
        return ownerRepository.findById(id);
    }

    public String update(UUID id, Owner updatedOwner) {
        Optional<Owner> existingOwner = ownerRepository.findById(id);
        if (existingOwner.isPresent()) {
            Owner owner = existingOwner.get();
            owner.setFirstName(updatedOwner.getFirstName());
            owner.setLastName(updatedOwner.getLastName());
            owner.setNationalId(updatedOwner.getNationalId());
            owner.setPhone(updatedOwner.getPhone());
            ownerRepository.save(owner);
            return "Owner updated successfully";
        }
        return "The owner with that id does not exist";
    }

    public String delete(UUID id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
            return "Owner deleted successfully";
        }
        return "The owner with that id does not exist";
    }
}
