
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public String save(Visit visit) {
        visitRepository.save(visit);
        return "Visit saved successfully";
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Optional<Visit> findById(UUID id) {
        return visitRepository.findById(id);
    }

    public String update(UUID id, Visit newVisit) {
        Optional<Visit> optionalVisit = visitRepository.findById(id);

        if (optionalVisit.isEmpty()) {
            return "The visit with that id does not exist";
        }

        Visit visit = optionalVisit.get();
        visit.setVisitDate(newVisit.getVisitDate());
        visit.setVisitorName(newVisit.getVisitorName());
        visit.setPurpose(newVisit.getPurpose());
        visit.setResident(newVisit.getResident());
        visit.setGuard(newVisit.getGuard());

        // Status is intentionally not changed by a general update.
        visitRepository.save(visit);
        return "Visit updated successfully";
    }

    public String delete(UUID id) {
        if (!visitRepository.existsById(id)) {
            return "The visit with that id does not exist";
        }

        visitRepository.deleteById(id);
        return "Visit deleted successfully";
    }
    
public List<Visit> findByStatus(VisitStatus status) {
    return visitRepository.findByStatusOrderByVisitDateAsc(status);
}

public List<Visit> findVisitsBetween(LocalDate start, LocalDate end) {
    return visitRepository
            .findByVisitDateBetweenOrderByVisitDateAsc(start, end);
}

}
