
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.Resident;
import com.example.HOSTEL.MNMGT.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveResident(@RequestBody Resident resident) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(residentService.save(resident));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resident>> getAllResidents() {
        return ResponseEntity.ok(residentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResidentById(@PathVariable UUID id) {
        return residentService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The resident with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateResident(
            @PathVariable UUID id,
            @RequestBody Resident resident) {

        String message = residentService.update(id, resident);

        if (message.equals("The resident with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResident(@PathVariable UUID id) {
        String message = residentService.delete(id);

        if (message.equals("The resident with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
    
@GetMapping("/by-last-name")
public ResponseEntity<List<Resident>> getResidentsByLastName(
        @RequestParam String lastName) {
    return ResponseEntity.ok(
            residentService.findByLastName(lastName)
    );
}

}
