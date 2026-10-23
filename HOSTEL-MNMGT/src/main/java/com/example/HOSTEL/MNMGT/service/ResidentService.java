
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Resident;
import com.example.HOSTEL.MNMGT.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public String save(Resident resident) {
        residentRepository.save(resident);
        return "Resident saved successfully";
    }

    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    public Optional<Resident> findById(UUID id) {
        return residentRepository.findById(id);
    }

    public String update(UUID id, Resident newResident) {
        Optional<Resident> optionalResident =
                residentRepository.findById(id);

        if (optionalResident.isEmpty()) {
            return "The resident with that id does not exist";
        }

        Resident resident = optionalResident.get();
        resident.setFirstName(newResident.getFirstName());
        resident.setLastName(newResident.getLastName());
        resident.setRegNumber(newResident.getRegNumber());
        resident.setRoomNumber(newResident.getRoomNumber());
        resident.setPhone(newResident.getPhone());

        residentRepository.save(resident);
        return "Resident updated successfully";
    }

    public String delete(UUID id) {
        if (!residentRepository.existsById(id)) {
            return "The resident with that id does not exist";
        }

        residentRepository.deleteById(id);
        return "Resident deleted successfully";
    }
    
public List<Resident> findByLastName(String lastName) {
    return residentRepository
            .findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}

}
