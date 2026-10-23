
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Guard;
import com.example.HOSTEL.MNMGT.repository.GuardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuardService {

    private final GuardRepository guardRepository;

    public GuardService(GuardRepository guardRepository) {
        this.guardRepository = guardRepository;
    }

    public String save(Guard guard) {
        guardRepository.save(guard);
        return "Guard saved successfully";
    }

    public List<Guard> findAll() {
        return guardRepository.findAll();
    }

    public Optional<Guard> findById(UUID id) {
        return guardRepository.findById(id);
    }

    public String update(UUID id, Guard newGuard) {
        Optional<Guard> optionalGuard = guardRepository.findById(id);

        if (optionalGuard.isEmpty()) {
            return "The guard with that id does not exist";
        }

        Guard guard = optionalGuard.get();
        guard.setFirstName(newGuard.getFirstName());
        guard.setLastName(newGuard.getLastName());
        guard.setPhone(newGuard.getPhone());
        guard.setGate(newGuard.getGate());
        guard.setShifts(newGuard.getShifts());

        guardRepository.save(guard);
        return "Guard updated successfully";
    }

    public String delete(UUID id) {
        if (!guardRepository.existsById(id)) {
            return "The guard with that id does not exist";
        }

        guardRepository.deleteById(id);
        return "Guard deleted successfully";
    }
}
