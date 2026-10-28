
package com.example.HOSTEL.MNMGT.controller;

import com.example.HOSTEL.MNMGT.domain.VisitLog;
import com.example.HOSTEL.MNMGT.service.VisitLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visit-logs")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam UUID visitId,
            @RequestBody VisitLog log) {

        String message = visitLogService.save(log, visitId);

        if (message.equals("The visit with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(message);
        }

        if (message.equals(
                "Visit logs are only for CHECKED_OUT visits")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/resident/{regNumber}")
    public ResponseEntity<List<VisitLog>> findByResident(
            @PathVariable String regNumber) {

        return ResponseEntity.ok(
                visitLogService.findByResident(regNumber));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {

        String message = visitLogService.delete(id);

        if (message.equals(
                "The visit log with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(message);
        }

        return ResponseEntity.ok(message);
    }
}

