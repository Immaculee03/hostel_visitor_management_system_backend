
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.Gate;
import com.example.HOSTEL.MNMGT.service.GateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveGate(@RequestBody Gate gate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gateService.save(gate));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Gate>> getAllGates() {
        return ResponseEntity.ok(gateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGateById(@PathVariable UUID id) {
        return gateService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The gate with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGate(
            @PathVariable UUID id,
            @RequestBody Gate gate) {

        String message = gateService.update(id, gate);

        if (message.equals("The gate with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGate(@PathVariable UUID id) {
        String message = gateService.delete(id);

        if (message.equals("The gate with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
    
@GetMapping("/free")
public ResponseEntity<List<Gate>> getFreeGates() {
    return ResponseEntity.ok(gateService.findFreeGates());
}

}
