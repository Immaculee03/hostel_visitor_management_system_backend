
package com.example.HOSTEL.MNMGT.service;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitLog;
import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.repository.VisitLogRepository;
import com.example.HOSTEL.MNMGT.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitRepository visitRepository;

    public VisitLogService(
            VisitLogRepository visitLogRepository,
            VisitRepository visitRepository) {

        this.visitLogRepository = visitLogRepository;
        this.visitRepository = visitRepository;
    }

    public String save(VisitLog log, UUID visitId) {

        Optional<Visit> optionalVisit =
                visitRepository.findById(visitId);

        if (optionalVisit.isEmpty()) {
            return "The visit with that id does not exist";
        }

        Visit visit = optionalVisit.get();

        if (visit.getStatus() != VisitStatus.CHECKED_OUT) {
            return "Visit logs are only for CHECKED_OUT visits";
        }

        log.setVisit(visit);
        log.setLoggedDate(LocalDate.now());

        visitLogRepository.save(log);

        return "Visit log saved successfully";
    }

    public List<VisitLog> findByResident(String regNumber) {

        return visitLogRepository
                .findByVisitResidentRegNumberOrderByLoggedDateDesc(
                        regNumber);
    }

    public String delete(UUID id) {

        if (!visitLogRepository.existsById(id)) {
            return "The visit log with that id does not exist";
        }

        visitLogRepository.deleteById(id);

        return "Visit log deleted successfully";
    }
}
