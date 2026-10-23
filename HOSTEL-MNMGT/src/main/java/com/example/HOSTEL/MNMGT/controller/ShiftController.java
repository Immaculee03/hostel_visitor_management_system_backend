
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.Shift;
import com.example.HOSTEL.MNMGT.service.ShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveShift(@RequestBody Shift shift) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(shiftService.save(shift));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Shift>> getAllShifts() {
        return ResponseEntity.ok(shiftService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable UUID id) {
        return shiftService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The shift with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateShift(
            @PathVariable UUID id,
            @RequestBody Shift shift) {

        String message = shiftService.update(id, shift);

        if (message.equals("The shift with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShift(@PathVariable UUID id) {
        String message = shiftService.delete(id);

        if (message.equals("The shift with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
