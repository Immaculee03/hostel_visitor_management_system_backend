
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.repository.VisitLogRepository;
import com.example.HOSTEL.MNMGT.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitLogRepository visitLogRepository;

    public VisitService(
            VisitRepository visitRepository,
            VisitLogRepository visitLogRepository) {
        this.visitRepository = visitRepository;
        this.visitLogRepository = visitLogRepository;
    }

    public String save(Visit visit) {
        if (visit.getVisitDate().isBefore(LocalDate.now())) {
            return "Visit date cannot be in the past";
        }

        if (visitRepository.existsByGuardAndVisitDateAndStatusNot(
                visit.getGuard(),
                visit.getVisitDate(),
                VisitStatus.DENIED)) {
            return "Guard is already supervising a visit on that date";
        }

        visit.setStatus(VisitStatus.REQUESTED);
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

        visitRepository.save(visit);
        return "Visit updated successfully";
    }

    public String delete(UUID id) {
        Optional<Visit> optionalVisit = visitRepository.findById(id);

        if (optionalVisit.isEmpty()) {
            return "The visit with that id does not exist";
        }

        Visit visit = optionalVisit.get();

        if (visitLogRepository.existsByVisit(visit)) {
            return "That visit has visit logs, delete them first";
        }

        visitRepository.delete(visit);
        return "Visit deleted successfully";
    }

    public List<Visit> findByStatus(VisitStatus status) {
        return visitRepository.findByStatusOrderByVisitDateAsc(status);
    }

    public List<Visit> findVisitsBetween(
            LocalDate start, LocalDate end) {
        return visitRepository
                .findByVisitDateBetweenOrderByVisitDateAsc(start, end);
    }

    public String updateStatus(UUID id, VisitStatus newStatus) {
        Optional<Visit> optionalVisit = visitRepository.findById(id);

        if (optionalVisit.isEmpty()) {
            return "The visit with that id does not exist";
        }

        Visit visit = optionalVisit.get();
        VisitStatus currentStatus = visit.getStatus();

        boolean validTransition = false;

        if (currentStatus == VisitStatus.REQUESTED) {
            validTransition = newStatus == VisitStatus.CHECKED_IN
                    || newStatus == VisitStatus.DENIED;
        } else if (currentStatus == VisitStatus.CHECKED_IN) {
            validTransition = newStatus == VisitStatus.CHECKED_OUT
                    || newStatus == VisitStatus.DENIED;
        }

        if (!validTransition) {
            return "Cannot change status from " + currentStatus
                    + " to " + newStatus;
        }

        visit.setStatus(newStatus);
        visitRepository.save(visit);

        return "Visit status updated successfully";
    }
    
public List<Object[]> countVisitsByStatus() {
    return visitRepository.countVisitsByStatus();
}
  public String cancelVisitsForGuardOnDate(UUID guardId, LocalDate date) {
    int count = visitRepository.cancelVisitsForGuardOnDate(guardId, date);
    return count + " visits cancelled";
}

}
