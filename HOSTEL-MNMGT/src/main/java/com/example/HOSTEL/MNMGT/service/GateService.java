
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Gate;
import com.example.HOSTEL.MNMGT.repository.GateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GateService {

    private final GateRepository gateRepository;

    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    public String save(Gate gate) {
        gateRepository.save(gate);
        return "Gate saved successfully";
    }

    public List<Gate> findAll() {
        return gateRepository.findAll();
    }

    public Optional<Gate> findById(UUID id) {
        return gateRepository.findById(id);
    }

    public String update(UUID id, Gate newGate) {
        Optional<Gate> optionalGate = gateRepository.findById(id);

        if (optionalGate.isEmpty()) {
            return "The gate with that id does not exist";
        }

        Gate gate = optionalGate.get();
        gate.setGateNumber(newGate.getGateNumber());
        gate.setName(newGate.getName());

        gateRepository.save(gate);
        return "Gate updated successfully";
    }

    public String delete(UUID id) {
        if (!gateRepository.existsById(id)) {
            return "The gate with that id does not exist";
        }

        gateRepository.deleteById(id);
        return "Gate deleted successfully";
    }
}
