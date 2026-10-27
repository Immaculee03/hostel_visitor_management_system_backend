
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.Guard;
import com.example.HOSTEL.MNMGT.service.GuardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/guards")
public class GuardController {

    private final GuardService guardService;

    public GuardController(GuardService guardService) {
        this.guardService = guardService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveGuard(@RequestBody Guard guard) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(guardService.save(guard));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Guard>> getAllGuards() {
        return ResponseEntity.ok(guardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuardById(@PathVariable UUID id) {
        return guardService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The guard with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGuard(
            @PathVariable UUID id,
            @RequestBody Guard guard) {

        String message = guardService.update(id, guard);

        if (message.equals("The guard with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuard(@PathVariable UUID id) {
        String message = guardService.delete(id);

        if (message.equals("The guard with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
    
@GetMapping("/by-shift")
public ResponseEntity<List<Guard>> getGuardsByShift(
        @RequestParam String name) {
    return ResponseEntity.ok(guardService.findByShiftName(name));
}
@PutMapping("/{guardId}/shifts/{shiftId}")
public ResponseEntity<String> addShift(
        @PathVariable UUID guardId,
        @PathVariable UUID shiftId) {

    String message = guardService.addShift(guardId, shiftId);

    if (message.equals("The guard with that id does not exist")
            || message.equals("That shift does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    if (message.equals("The guard already has that shift")) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
    }

    return ResponseEntity.ok(message);
}
@PatchMapping("/{guardId}/gate")
public ResponseEntity<String> changeGate(
        @PathVariable UUID guardId,
        @RequestParam int gateNumber) {

    String message = guardService.changeGate(guardId, gateNumber);

    if (message.equals("The guard with that id does not exist")
            || message.equals("The gate with that gateNumber does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    if (message.equals("That gate is taken by another guard")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    return ResponseEntity.ok(message);
}

}
