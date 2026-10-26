
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ResidentRepository
        extends JpaRepository<Resident, UUID> {
            
List<Resident> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

@Query("SELECT DISTINCT v.resident FROM Visit v WHERE v.guard.id = :guardId")
List<Resident> findResidentsVisitedByGuard(@Param("guardId") UUID guardId);

}

