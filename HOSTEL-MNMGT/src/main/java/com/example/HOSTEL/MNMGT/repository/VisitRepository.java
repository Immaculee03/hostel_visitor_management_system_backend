
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Visit;
import com.example.HOSTEL.MNMGT.domain.VisitStatus;
import com.example.HOSTEL.MNMGT.domain.Guard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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

   
    @Query("SELECT v.status, COUNT(v) FROM Visit v GROUP BY v.status")
    List<Object[]> countVisitsByStatus();
    // F2: Cancel a guard's visits on a specific date, except checked-out visits
@Modifying
@Transactional
@Query("""
    UPDATE Visit v
    SET v.status = com.example.HOSTEL.MNMGT.domain.VisitStatus.DENIED
    WHERE v.guard.id = :guardId
      AND v.visitDate = :date
      AND v.status <> com.example.HOSTEL.MNMGT.domain.VisitStatus.CHECKED_OUT
    """)
int cancelVisitsForGuardOnDate(
    @Param("guardId") UUID guardId,
    @Param("date") LocalDate date
);
}
