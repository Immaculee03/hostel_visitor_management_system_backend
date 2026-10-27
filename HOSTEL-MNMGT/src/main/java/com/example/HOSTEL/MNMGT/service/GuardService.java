
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Gate;
import com.example.HOSTEL.MNMGT.domain.Guard;
import com.example.HOSTEL.MNMGT.repository.GuardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.HOSTEL.MNMGT.domain.Shift;
import com.example.HOSTEL.MNMGT.repository.ShiftRepository;
import java.util.ArrayList;
import com.example.HOSTEL.MNMGT.repository.GateRepository;


@Service
public class GuardService {
private final GuardRepository guardRepository;
private final ShiftRepository shiftRepository;
private final GateRepository gateRepository;

public GuardService(GuardRepository guardRepository,
                    ShiftRepository shiftRepository,
                    GateRepository gateRepository) {
    this.guardRepository = guardRepository;
    this.shiftRepository = shiftRepository;
    this.gateRepository = gateRepository;
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

    List<Shift> shifts = new ArrayList<>();

    if (newGuard.getShifts() != null) {
        for (Shift shift : newGuard.getShifts()) {
            if (shift.getId() != null) {
                shiftRepository.findById(shift.getId())
                        .ifPresent(shifts::add);
            }
        }
    }

    guard.setShifts(shifts);
    guardRepository.saveAndFlush(guard);

    return "Guard updated successfully";
}
    public String delete(UUID id) {
        if (!guardRepository.existsById(id)) {
            return "The guard with that id does not exist";
        }

        guardRepository.deleteById(id);
        return "Guard deleted successfully";
    }
    
public List<Guard> findByShiftName(String name) {
    return guardRepository.findByShiftNameContainingIgnoreCase(name);
}
public String addShift(UUID guardId, UUID shiftId) {
    Optional<Guard> optionalGuard = guardRepository.findById(guardId);

    if (optionalGuard.isEmpty()) {
        return "The guard with that id does not exist";
    }

    Optional<Shift> optionalShift = shiftRepository.findById(shiftId);

    if (optionalShift.isEmpty()) {
        return "That shift does not exist";
    }

    Guard guard = optionalGuard.get();
    Shift shift = optionalShift.get();

    for (Shift existingShift : guard.getShifts()) {
        if (existingShift.getId().equals(shiftId)) {
            return "The guard already has that shift";
        }
    }

    guard.getShifts().add(shift);
    guardRepository.save(guard);

    return "Shift added to the guard";
}
public String changeGate(UUID guardId, int gateNumber) {
    Optional<Guard> optionalGuard = guardRepository.findById(guardId);

    if (optionalGuard.isEmpty()) {
        return "The guard with that id does not exist";
    }

    Optional<Gate> optionalGate =
            gateRepository.findByGateNumber(gateNumber);

    if (optionalGate.isEmpty()) {
        return "The gate with that gateNumber does not exist";
    }

    Guard guard = optionalGuard.get();
    Gate gate = optionalGate.get();

    if (guardRepository.existsByGate(gate)) {
        if (guard.getGate() == null
                || !guard.getGate().getId().equals(gate.getId())) {
            return "That gate is taken by another guard";
        }
    }

    guard.setGate(gate);
    guardRepository.save(guard);

    return "Gate changed successfully";
}
}
