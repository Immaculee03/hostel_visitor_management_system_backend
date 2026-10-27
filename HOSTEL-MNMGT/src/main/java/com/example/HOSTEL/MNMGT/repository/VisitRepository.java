
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.domain.Guard;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, UUID> {

    List<Visit> findByVisitDateBetweenOrderByVisitDateAsc(
            LocalDate start,
            LocalDate end
    );

    List<Visit> findByStatusOrderByVisitDateAsc(VisitStatus status);

    boolean existsByGuardAndVisitDateAndStatusNot(
            Guard guard,
            LocalDate visitDate,
            VisitStatus status
    );
}
