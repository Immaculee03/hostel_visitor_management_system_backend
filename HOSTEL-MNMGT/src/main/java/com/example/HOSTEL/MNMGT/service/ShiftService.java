
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Shift;
import com.example.HOSTEL.MNMGT.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public String save(Shift shift) {
        shiftRepository.save(shift);
        return "Shift saved successfully";
    }

    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    public Optional<Shift> findById(UUID id) {
        return shiftRepository.findById(id);
    }

    public String update(UUID id, Shift newShift) {
        Optional<Shift> optionalShift = shiftRepository.findById(id);

        if (optionalShift.isEmpty()) {
            return "The shift with that id does not exist";
        }

        Shift shift = optionalShift.get();
        shift.setName(newShift.getName());

        shiftRepository.save(shift);
        return "Shift updated successfully";
    }

    public String delete(UUID id) {
        if (!shiftRepository.existsById(id)) {
            return "The shift with that id does not exist";
        }

        shiftRepository.deleteById(id);
        return "Shift deleted successfully";
    }
    
public List<Shift> findUnusedShifts() {
    return shiftRepository.findUnusedShifts();
}

}
