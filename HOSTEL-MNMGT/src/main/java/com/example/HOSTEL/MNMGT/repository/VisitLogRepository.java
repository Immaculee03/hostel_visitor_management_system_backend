
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitLogRepository extends JpaRepository<VisitLog, UUID> {

    List<VisitLog> findByVisit(Visit visit);

    boolean existsByVisit(Visit visit);

    List<VisitLog> findByVisitResidentRegNumberOrderByLoggedDateDesc(
            String regNumber);
}
