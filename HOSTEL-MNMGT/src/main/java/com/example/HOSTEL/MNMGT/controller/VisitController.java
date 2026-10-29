
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    
@PostMapping("/save")
public ResponseEntity<String> saveVisit(@RequestBody Visit visit) {

    String message = visitService.save(visit);

    if (message.equals("Visit date cannot be in the past")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    if (message.equals(
            "Guard is already supervising a visit on that date")) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
    }

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(message);
}


    @GetMapping("/all")
    public ResponseEntity<List<Visit>> getAllVisits() {
        return ResponseEntity.ok(visitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVisitById(@PathVariable UUID id) {
        return visitService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The visit with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVisit(
            @PathVariable UUID id,
            @RequestBody Visit visit) {

        String message = visitService.update(id, visit);

        if (message.equals("The visit with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteVisit(@PathVariable UUID id) {
    String message = visitService.delete(id);

    if (message.equals("The visit with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    if (message.equals("That visit has visit logs, delete them first")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    return ResponseEntity.ok(message);
}

    
@GetMapping("/by-status")
public ResponseEntity<List<Visit>> getVisitsByStatus(
        @RequestParam VisitStatus status) {
    return ResponseEntity.ok(visitService.findByStatus(status));
}

@GetMapping("/between")
public ResponseEntity<List<Visit>> getVisitsBetween(
        @RequestParam LocalDate start,
        @RequestParam LocalDate end) {

    return ResponseEntity.ok(
            visitService.findVisitsBetween(start, end)
    );
}
@PatchMapping("/{id}/status")
public ResponseEntity<String> updateVisitStatus(
        @PathVariable UUID id,
        @RequestParam VisitStatus status) {

    String message = visitService.updateStatus(id, status);

    if (message.equals("The visit with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    if (message.startsWith("Cannot change status")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    return ResponseEntity.ok(message);
}

@GetMapping("/stats/by-status")
public ResponseEntity<List<Object[]>> countVisitsByStatus() {
    return ResponseEntity.ok(visitService.countVisitsByStatus());
}
@PatchMapping("/cancel-day")
public ResponseEntity<String> cancelVisitsForGuardOnDate(
        @RequestParam UUID guardId,
        @RequestParam LocalDate date) {

    String message = visitService.cancelVisitsForGuardOnDate(guardId, date);
    return ResponseEntity.ok(message);
}

}
